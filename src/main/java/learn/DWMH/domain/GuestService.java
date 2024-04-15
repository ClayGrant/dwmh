package learn.DWMH.domain;

import learn.DWMH.data.GuestRepository;
import learn.DWMH.data.DataException;
import learn.DWMH.models.Guest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository){this.repository = repository;}

    public List<Guest> findByGuestEmail(String email){
        return repository.findAll().stream()
                .filter(i -> i.getEmail().equals(email))
                .collect(Collectors.toList());
    }

    public List<Guest> findByGuestId(int id){
        return repository.findAll().stream()
                .filter(i -> i.getId() == id)
                .collect(Collectors.toList());
    }
}
