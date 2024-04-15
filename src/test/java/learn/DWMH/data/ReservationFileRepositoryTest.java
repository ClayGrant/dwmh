package learn.DWMH.data;

import static org.junit.jupiter.api.Assertions.*;

import learn.DWMH.models.Guest;
import learn.DWMH.models.Host;
import learn.DWMH.models.Reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

class ReservationFileRepositoryTest {

    static final String SEED_PATH = "./data/seed-9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv";
    static final String TEST_PATH = "./data/reservations_test/test-9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv";
    static final String TEST_DIR = "./data/reservations_test";

    final String hostId = "test-9d469342-ad0b-4f5a-8d28-e81e690ba29a";
    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindById(){
        List<Reservation> reservations = repository.findByHostId(hostId);
        assertEquals(1, reservations.size());
    }

    @Test
    void shouldNotFindNullId(){
        List<Reservation> reservations = repository.findByHostId(null);
        assertEquals(0, reservations.size());
    }

    @Test
    void shouldNotFindWrongId(){
        List<Reservation> reservations = repository.findByHostId("Wrong");
        assertEquals(0, reservations.size());
    }

    @Test
    void shouldAdd() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setHostId(hostId);
        reservation.setStartDate(LocalDate.of(2024, 5, 5));
        reservation.setEndDate(LocalDate.of(2024, 5, 7));
        reservation.setTotal(BigDecimal.valueOf(340.99));

        Guest guest = new Guest();
        guest.setId(900);
        reservation.setGuest(guest);
        reservation.setGuestId(guest.getId());

        Host host = new Host();
        host.setId(hostId);
        reservation.setHost(host);

        reservation = repository.add(reservation);

        assertNotNull(reservation);

        assertEquals(2, reservation.getId());
    }

    @Test
    void shouldUpdate() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setHostId(hostId);
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2024, 7, 3));
        reservation.setEndDate(LocalDate.of(2024, 7, 4));
        reservation.setGuestId(18);
        reservation.setTotal(BigDecimal.valueOf(900));

        boolean success = repository.update(reservation);
        assertTrue(success);
    }

    @Test
    void shouldNotUpdateMissing() throws DataException{

        Reservation reservation = new Reservation();
        reservation.setHostId(hostId);
        reservation.setId(100);
        reservation.setStartDate(LocalDate.of(2024, 7, 3));
        reservation.setEndDate(LocalDate.of(2024, 7, 4));
        reservation.setGuestId(18);
        reservation.setTotal(BigDecimal.valueOf(900));

        boolean success = repository.update(reservation);
        assertFalse(success);

    }

    @Test
    void shouldNotUpdateNull() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setHostId(hostId);

        boolean success = repository.update(reservation);
        assertFalse(success);
    }

    @Test
    void shouldDelete() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setHostId(hostId);
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2024, 7, 3));
        reservation.setEndDate(LocalDate.of(2024, 7, 4));
        reservation.setGuestId(18);
        reservation.setTotal(BigDecimal.valueOf(900));

        boolean success = repository.delete(reservation);
        assertTrue(success);
    }

    @Test
    void shouldNotDeleteMissing() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setHostId(hostId);
        reservation.setId(100);
        reservation.setStartDate(LocalDate.of(2024, 7, 3));
        reservation.setEndDate(LocalDate.of(2024, 7, 4));
        reservation.setGuestId(18);
        reservation.setTotal(BigDecimal.valueOf(900));

        boolean success = repository.delete(reservation);
        assertFalse(success);
    }

    @Test
    void shouldNotDeleteNull() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setHostId(hostId);

        boolean success = repository.delete(reservation);
        assertFalse(success);
    }


}