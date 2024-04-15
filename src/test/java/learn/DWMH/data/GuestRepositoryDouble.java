package learn.DWMH.data;

import learn.DWMH.models.Guest;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GuestRepositoryDouble implements GuestRepository{

    public final static Guest GUEST = testGuest();

    private static Guest testGuest(){
        Guest guest = new Guest();
        guest.setId(9999);
        guest.setFirstName("Honky");
        guest.setLastName("Tonk");
        guest.setEmail("honkytonk@tonkyhonk.com");
        guest.setPhone("(123) 4567890");
        guest.setState("NY");
        return guest;
    }
    private final ArrayList<Guest> guests = new ArrayList<>();
    public GuestRepositoryDouble() {guests.add(GUEST);}

    @Override
    public List<Guest> findAll(){
        return new ArrayList<>(guests);
    }

    @Override
    public Guest findByGuestEmail(String email){
        return findAll().stream().filter(g -> g.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    @Override
    public Guest findByGuestId(int id){
        return findAll().stream().filter(g -> g.getId() == id).findFirst().orElse(null);
    }

}