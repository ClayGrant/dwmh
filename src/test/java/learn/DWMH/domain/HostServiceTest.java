package learn.DWMH.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import learn.DWMH.data.HostRepositoryDouble;
import learn.DWMH.models.Host;

import java.util.List;

class HostServiceTest {

    HostService service;
    final HostRepositoryDouble hostRepository = new HostRepositoryDouble();

    @BeforeEach
    void setUp(){
        service = new HostService(hostRepository);
    }

    @Test
    void shouldFindEmail(){
        String email ="honkytonk@tonkyhonk.com";
        List<Host> result = service.findByHostEmail(email);
        assertEquals("honkytonk@tonkyhonk.com", result.get(0).getEmail());
        assertEquals("(123) 4567890", result.get(0).getPhone());
    }

    @Test
    void shouldNotFindMissingEmail(){
        String email = "Test@test.com";
        List<Host> result = service.findByHostEmail(email);
        assertEquals(0, result.size());
    }

    @Test
    void shouldNotFindNullEmail(){
        String email = null;
        List<Host> result = service.findByHostEmail(email);
        assertEquals(0, result.size());
    }

    @Test
    void shouldFindId(){
        String id ="testId";
        List<Host> result = service.findByHostId(id);
        assertEquals("honkytonk@tonkyhonk.com", result.get(0).getEmail());
        assertEquals("(123) 4567890", result.get(0).getPhone());
    }

    @Test
    void shouldNotFindMissingId(){
        String id = "dingus";
        List<Host> result = service.findByHostId(id);
        assertEquals(0, result.size());
    }

    @Test
    void shouldNotFindNullId(){
        String id = null;
        List<Host> result = service.findByHostId(id);
        assertEquals(0, result.size());
    }

}