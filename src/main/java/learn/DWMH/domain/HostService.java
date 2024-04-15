package learn.DWMH.domain;

import learn.DWMH.models.Host;
import learn.DWMH.data.HostRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository){this.repository = repository;}

    public List<Host> findByHostEmail(String email){
        return repository.findAll().stream()
                .filter(i -> i.getEmail().equals(email))
                .collect(Collectors.toList());
    }

    public List<Host> findByHostId(String id){
        return repository.findAll().stream()
                .filter(i -> i.getId().equals(id))
                .collect(Collectors.toList());
    }

}
