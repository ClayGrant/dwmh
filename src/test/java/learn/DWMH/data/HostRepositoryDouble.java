package learn.DWMH.data;

import learn.DWMH.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class HostRepositoryDouble implements HostRepository{

    public final static Host HOST = testHost();

    private static Host testHost(){
        Host host = new Host();
        host.setId("testId");
        host.setLastName("Tonk");
        host.setEmail("honkytonk@tonkyhonk.com");
        host.setPhone("(123) 4567890");
        host.setAddress("456 Test St.");
        host.setCity("Test City");
        host.setState("TN");
        host.setPostalCode(12345);
        host.setStandardRate(BigDecimal.valueOf(123.45));
        host.setWeekendRate(BigDecimal.valueOf(234.56));
        return host;
    }

    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() { hosts.add(HOST); }

    @Override
    public List<Host> findAll(){
        return new ArrayList<>(hosts);
    }

    @Override
    public Host findByHostEmail(String email){
        return findAll().stream().filter(g -> g.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    @Override
    public Host findByHostId(String id){
        return findAll().stream().filter(g -> Objects.equals(g.getId(), id)).findFirst().orElse(null);
    }

}