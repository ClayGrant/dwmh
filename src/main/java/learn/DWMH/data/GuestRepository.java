package learn.DWMH.data;

import learn.DWMH.models.Guest;

import java.util.List;
public interface GuestRepository {

    List<Guest> findAll();
    Guest findByGuestEmail(String email);
    Guest findByGuestId(int id);
}
