package learn.DWMH.domain;

import static org.junit.jupiter.api.Assertions.*;

import learn.DWMH.data.GuestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import learn.DWMH.data.GuestRepositoryDouble;
import learn.DWMH.models.Guest;

import java.util.List;

class GuestServiceTest {

    GuestService service;
    final GuestRepositoryDouble guestRepository = new GuestRepositoryDouble();

    @BeforeEach
    void setUp(){
        service = new GuestService(guestRepository);
    }

    @Test
    void shouldFindEmail(){
        String email ="honkytonk@tonkyhonk.com";
        List<Guest> result = service.findByGuestEmail(email);
        assertEquals("honkytonk@tonkyhonk.com", result.get(0).getEmail());
        assertEquals("(123) 4567890", result.get(0).getPhone());
    }

    @Test
    void shouldNotFindMissingEmail(){
        String email = "Test@test.com";
        List<Guest> result = service.findByGuestEmail(email);
        assertEquals(0, result.size());
    }

    @Test
    void shouldNotFindNullEmail(){
        String email = null;
        List<Guest> result = service.findByGuestEmail(email);
        assertEquals(0, result.size());
    }

    @Test
    void shouldFindId(){
        int id = 9999;
        List<Guest> result = service.findByGuestId(id);
        assertEquals("honkytonk@tonkyhonk.com", result.get(0).getEmail());
        assertEquals("(123) 4567890", result.get(0).getPhone());
    }

    @Test
    void shouldNotFindMissingId(){
        int id = 1;
        List<Guest> result = service.findByGuestId(id);
        assertEquals(0, result.size());
    }

}