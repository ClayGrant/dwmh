package learn.DWMH.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import learn.DWMH.models.Guest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

class GuestFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/guests-seed.csv";
    static final String TEST_FILE_PATH = "./data/guests-test.csv";

    GuestFileRepository repository = new GuestFileRepository(SEED_FILE_PATH);

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll(){
        List<Guest> guests = repository.findAll();
        assertEquals(2, guests.size());
    }

    @Test
    void shouldFindById(){
        Guest guest = repository.findByGuestId(1);
        assertNotNull(guest);
        assertEquals("slomas0@mediafire.com", guest.getEmail());
    }

    @Test
    void shouldNotFindMissingId(){
        Guest guest = repository.findByGuestId(3);
        assertNull(guest);
    }

    @Test
    void shouldFindByEmail(){
        Guest guest = repository.findByGuestEmail("slomas0@mediafire.com");
        assertNotNull(guest);
        assertEquals("Sullivan", guest.getFirstName());
    }

    @Test
    void shouldNotFindNull(){
        Guest guest = repository.findByGuestEmail(null);
        assertNull(guest);
    }

    @Test
    void shouldNotFindMissingEmail(){
        Guest guest = repository.findByGuestEmail("honkytonk@tonkyhonk.com");
        assertNull(guest);
    }

}