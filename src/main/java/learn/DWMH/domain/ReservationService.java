package learn.DWMH.domain;

import learn.DWMH.data.DataException;
import learn.DWMH.data.HostRepository;
import learn.DWMH.data.GuestRepository;
import learn.DWMH.data.ReservationRepository;
import learn.DWMH.models.Host;
import learn.DWMH.models.Reservation;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationService {

    private final HostRepository hostRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(HostRepository hostRepository, GuestRepository guestRepository, ReservationRepository reservationRepository){
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.hostRepository = hostRepository;
    }

    public List<Reservation> findByHostId(String hostId) throws DataException{
        List<Reservation> result = reservationRepository.findByHostId(hostId);

        for (Reservation reservation : result){
            reservation.setHostId(reservation.getHostId());
            reservation.setId(reservation.getId());
        }

        return result.stream().sorted(Comparator.comparing(Reservation::getStartDate)).collect(Collectors.toList());
    }

    //do I need this?
//    public List<Reservation> filterByGuestEmail(String guestEmail, List<Reservation> hostReservations){
//        int guestId = guestRepository.findByGuestEmail(guestEmail).getId();
//        Stream<Reservation> streamResult = hostReservations.stream().filter(i -> i.getGuestId() == guestId);
//
//        List<Reservation> result = streamResult.collect(Collectors.toList());
//
//        return result;
//    }

    public Result<Reservation> add(Reservation reservation) throws DataException{
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()){
            return result;
        }

        reservation.setGuest(guestRepository.findByGuestId(reservation.getGuestId()));
        reservation.setHost(hostRepository.findByHostId(reservation.getHostId()));
        reservation.setTotal(calculateTotal(reservation));
        result.setPayload(reservationRepository.add(reservation));

        return result;
    }

    public Result<Reservation> delete(Reservation reservation) throws DataException{
        Result<Reservation> result = new Result<>();
        if (reservation.getStartDate().isBefore(LocalDate.now())){
            result.addErrorMessage("Cannot cancel a past reservation.");
        }
        if (result.isSuccess()){
            if (reservationRepository.delete(reservation)){
                result.setPayload(reservation);
            }
            else {
                String missing = String.format("Reservation ID %s not found.", reservation.getId());
                result.addErrorMessage(missing);
            }
        }
        return result;
    }

    public Result<Reservation> update(Reservation reservation) throws DataException{
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()){
            return result;
        }

        if (reservation.getStartDate().isBefore(LocalDate.now())){
            result.addErrorMessage("Cannot update a past reservation.");
        }

        if (result.isSuccess()){

            if (reservationRepository.update(reservation)){
                result.setPayload(reservation);
                reservation.setTotal(calculateTotal(reservation));
            }
            else {
                String missing = String.format("Reservation ID %s not found.", reservation.getId());
                result.addErrorMessage(missing);
            }

        }

        return result;

    }

    private BigDecimal calculateTotal(Reservation reservation){
        String hostId = reservation.getHostId();
        Host host = hostRepository.findByHostId(hostId);
        BigDecimal standard = host.getStandardRate();
        BigDecimal weekend = host.getWeekendRate();
        LocalDate start = reservation.getStartDate();
        LocalDate end = reservation.getEndDate();

        int nights = (int)Math.abs(DAYS.between(start, end));//dates and nights

        int standardTotal = 0;
        int weekendTotal = 0;//counters

        LocalDate date = start;
        for (int i = 0; i < nights; i++) {
            if (date.getDayOfWeek() != DayOfWeek.FRIDAY && date.getDayOfWeek() != DayOfWeek.SATURDAY) {
                standardTotal++;
            } else {
                weekendTotal++;
            }
            date = date.plusDays(1);
        }

        BigDecimal standardPrice = standard.multiply(BigDecimal.valueOf(standardTotal));
        BigDecimal weekendPrice = weekend.multiply(BigDecimal.valueOf(weekendTotal));
        return standardPrice.add(weekendPrice).setScale(2, RoundingMode.HALF_UP);
    }

    private Result<Reservation> validate(Reservation reservation) throws DataException{
        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()){
            return result;
        }

        validateFields(reservation, result);
        if (!result.isSuccess()){
            return result;
        }

        validateExsist(reservation, result);

        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation){
        Result<Reservation> result = new Result<>();

        if (reservation == null){
            result.addErrorMessage("No reservation.");
            return result;
        }

        if (reservation.getStartDate()==null){
            result.addErrorMessage("Need a start date.");
            return result;
        }

        if (reservation.getEndDate()==null){
            result.addErrorMessage("Need an end date");
            return result;
        }

        if (reservation.getGuest()==null){
            result.addErrorMessage("Guest is required");
            return result;
        }

        if (reservation.getHost()==null){
            result.addErrorMessage("Host is required.");
        }
        return result;
    }

    private void validateFields(Reservation reservation, Result<Reservation> result){
        if (reservation.getStartDate().isBefore(LocalDate.now())){
            result.addErrorMessage("Start date cannot be in the past.");
        }
        if (reservation.getStartDate().isAfter(reservation.getEndDate())){
            result.addErrorMessage("End date cannot be before start date.");
        }

        List<Reservation> reservations = reservationRepository.findByHostId(reservation.getHostId());
        for(Reservation r : reservations){
            LocalDate currentStart = r.getStartDate();
            LocalDate currentEnd = r.getEndDate();
            if (reservation.getId() != r.getId()){
                if(currentStart.isEqual(reservation.getStartDate())){
                    result.addErrorMessage("Another reservation has the same start.");
                }
                if (reservation.getStartDate().isBefore(currentStart) && reservation.getEndDate().isAfter(currentStart)){
                    result.addErrorMessage("The reservation would end after the start of another reservation.");
                }
                if (reservation.getStartDate().isAfter(currentStart) && reservation.getStartDate().isBefore(currentEnd)){
                    result.addErrorMessage("The reservation would start before the end of another reservation.");
                }
                if (reservation.getStartDate().isAfter(currentStart) && reservation.getEndDate().isBefore(currentEnd)){
                    result.addErrorMessage("The reservation would be inside of another reservation.");
                }
            }
        }
    }

    private void validateExsist(Reservation reservation, Result<Reservation> result){
        if (reservation.getHost().getId()==null || hostRepository.findByHostId(reservation.getHost().getId())==null){
            result.addErrorMessage("Host does not exist.");
        }

        if (guestRepository.findByGuestId(reservation.getGuest().getId())==null){
            result.addErrorMessage("Guest does not exist.");
        }
    }


}
