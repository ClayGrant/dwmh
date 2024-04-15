package learn.DWMH.data;

import learn.DWMH.models.Host;

import java.util.List;

public interface HostRepository {

    List<Host> findAll();
    Host findByHostId(String id);
    Host findByHostEmail(String email);
}
