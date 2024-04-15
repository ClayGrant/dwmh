package learn.DWMH.data;

import static org.junit.jupiter.api.Assertions.*;

import learn.DWMH.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {

    private final static Reservation RESERVATION = testRes();
    private static Reservation testRes(){
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2024,9,9));
        reservation.setEndDate(LocalDate.of(2024, 9, 12));
        reservation.setGuestId(GuestRepositoryDouble.GUEST.getId());
        reservation.setHostId(HostRepositoryDouble.HOST.getId());
        reservation.setTotal(BigDecimal.valueOf(300));
        return reservation;
    }

    private final static Reservation RESERVATION2 = testRes2();
    private static Reservation testRes2(){
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setStartDate(LocalDate.of(2024,5,9));
        reservation.setEndDate(LocalDate.of(2024, 5, 12));
        reservation.setGuestId(GuestRepositoryDouble.GUEST.getId());
        reservation.setHostId(HostRepositoryDouble.HOST.getId());
        reservation.setTotal(BigDecimal.valueOf(350));
        return reservation;
    }

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        reservations.add(testRes());
        reservations.add(testRes2());
    }

    @Override
    public List<Reservation> findByHostId(String id){
        return reservations.stream().filter(i -> i.getHostId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException{
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException{
        List<Reservation> all = reservations;
        for (int i=0; i<all.size(); i++){
            if (reservations.get(i).getId() == reservation.getId() && reservations.get(i).getGuestId()==reservation.getGuestId()){
                all.set(i, reservation);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException{
        List<Reservation> all = reservations;
        for (int i=0; i<all.size(); i++){
            if (reservations.get(i).getId() == reservation.getId() && reservations.get(i).getGuestId()==reservation.getGuestId()){
                all.remove(i);
                return true;
            }
        }
        return false;
    }

}