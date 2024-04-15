package learn.DWMH.domain;

import static org.junit.jupiter.api.Assertions.*;

import learn.DWMH.data.DataException;
import learn.DWMH.data.HostRepositoryDouble;
import learn.DWMH.data.GuestRepositoryDouble;
import learn.DWMH.data.ReservationRepositoryDouble;
import learn.DWMH.models.Reservation;
import learn.DWMH.models.Guest;
import learn.DWMH.models.Host;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

class ReservationServiceTest {

    ReservationService service;
    final ReservationRepositoryDouble reservationRepository = new ReservationRepositoryDouble();
    final HostRepositoryDouble hostRepository = new HostRepositoryDouble();
    final GuestRepositoryDouble guestRepository = new GuestRepositoryDouble();

    @BeforeEach
    void setUp() {
        service = new ReservationService(hostRepository, guestRepository, reservationRepository);
    }

    @Test
    void shouldFindByEmail() throws DataException{
        String id = "testId";
        List<Reservation> result = service.findByHostId(id);
        assertNotNull(result);
    }

    @Test
    void shouldNotFindMissingId() throws DataException{
        String id = "nope";
        List<Reservation> result = service.findByHostId(id);
        assertEquals(0, result.size());
    }

    @Test
    void shouldNotFindNullId() throws DataException{
        String id = null;
        List<Reservation> result = service.findByHostId(id);
        assertEquals(0, result.size());
    }

    @Test
    void shouldAdd() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 6, 7));
        reservation.setEndDate(LocalDate.of(2024, 6, 10));
        reservation.setHostId("testId");
        reservation.setGuestId(9999);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldntAddNull() throws DataException{
        Reservation reservation = new Reservation();
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldntAddNoHost() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 6, 7));
        reservation.setEndDate(LocalDate.of(2024, 6, 10));
        reservation.setGuestId(9999);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldntAddNoGuest() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 6, 7));
        reservation.setEndDate(LocalDate.of(2024, 6, 10));
        reservation.setHostId("testId");
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldntAddPastStart() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 3, 7));
        reservation.setEndDate(LocalDate.of(2024, 3, 10));
        reservation.setHostId("testId");
        reservation.setGuestId(9999);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldntAddStartBeforeEnd() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 6, 10));
        reservation.setEndDate(LocalDate.of(2024, 6, 7));
        reservation.setHostId("testId");
        reservation.setGuestId(9999);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldntAddStartSame() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 5, 9));
        reservation.setEndDate(LocalDate.of(2024, 6, 10));
        reservation.setHostId("testId");
        reservation.setGuestId(9999);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldntAddStartOverlap() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 5, 10));
        reservation.setEndDate(LocalDate.of(2024, 6, 10));
        reservation.setHostId("testId");
        reservation.setGuestId(9999);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldntAddEndOverlap() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 5, 8));
        reservation.setEndDate(LocalDate.of(2024, 5, 10));
        reservation.setHostId("testId");
        reservation.setGuestId(9999);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldntAddInsideOverlap() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 5, 10));
        reservation.setEndDate(LocalDate.of(2024, 5, 11));
        reservation.setHostId("testId");
        reservation.setGuestId(9999);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldDelete() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2024,9,9));
        reservation.setEndDate(LocalDate.of(2024, 9, 12));
        reservation.setGuestId(9999);
        reservation.setHostId("testId");
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.delete(reservation);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldntDeletePast() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2024,2,9));
        reservation.setEndDate(LocalDate.of(2024, 9, 12));
        reservation.setGuestId(9999);
        reservation.setHostId("testId");
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.delete(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldntDeleteMissingId() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setId(200);
        reservation.setStartDate(LocalDate.of(2024,9,9));
        reservation.setEndDate(LocalDate.of(2024, 9, 12));
        reservation.setGuestId(9999);
        reservation.setHostId("testId");
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.delete(reservation);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldUpdate() throws DataException{

        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 6, 7));
        reservation.setEndDate(LocalDate.of(2024, 6, 10));
        reservation.setHostId("testId");
        reservation.setGuestId(9999);
        reservation.setId(1);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());

    }

    //not testing the rest of the date overlaps and such because those use validate and that's the same for update and add
    @Test
    void shouldntUpdatePast() throws DataException{

        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 2, 7));
        reservation.setEndDate(LocalDate.of(2024, 6, 10));
        reservation.setHostId("testId");
        reservation.setGuestId(9999);
        reservation.setId(1);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());

    }

    @Test
    void shouldntUpdateNull() throws DataException{

        Reservation reservation = new Reservation();

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());

    }

    @Test
    void shouldntUpdateMissingId() throws DataException{

        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2024, 2, 7));
        reservation.setEndDate(LocalDate.of(2024, 6, 10));
        reservation.setHostId("testId");
        reservation.setGuestId(9999);
        reservation.setId(100);
        reservation.setGuest(guestRepository.findByGuestId(9999));
        reservation.setHost(hostRepository.findByHostId("testId"));
        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());

    }

}