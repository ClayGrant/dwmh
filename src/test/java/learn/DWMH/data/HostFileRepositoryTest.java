package learn.DWMH.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import learn.DWMH.models.Host;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
class HostFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/hosts-seed.csv";
    static final String TEST_FILE_PATH = "./data/hosts-test.csv";

    HostFileRepository repository = new HostFileRepository(SEED_FILE_PATH);

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll(){
        List<Host> hosts = repository.findAll();
        assertEquals(2, hosts.size());
    }

    @Test
    void shouldFindById(){
        Host host = repository.findByHostId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertNotNull(host);
        assertEquals("eyearnes0@sfgate.com", host.getEmail());
    }

    @Test
    void shouldNotFindMissingId(){
        Host host = repository.findByHostId("3edda6bc-ab95-49a8-8962-d50b53f84b13");
        assertNull(host);
    }

    @Test
    void shouldFindByEmail(){
        Host host = repository.findByHostEmail("eyearnes0@sfgate.com");
        assertNotNull(host);
        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", host.getId());
    }

    @Test
    void shouldNotFindNull(){
        Host host = repository.findByHostEmail(null);
        assertNull(host);
    }

    @Test
    void shouldNotFindMissingEmail(){
        Host host = repository.findByHostEmail("honkytonk@tonkyhonk.com");
        assertNull(host);
    }

}