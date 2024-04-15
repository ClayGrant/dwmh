package learn.DWMH.models;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Reservation {

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private int guestId;
    private Host host;
    private String hostId;
    private BigDecimal total;

    public Reservation(){

    }

    public Reservation(int id, LocalDate startDate, LocalDate endDate, Guest guest, Host host, BigDecimal total) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guest = guest;
        this.host = host;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public int getGuestId(){return guestId;}
    public void setGuestId(int guestId){ this.guestId = guestId; }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getHostId(){return  hostId;}
    public void setHostId(String hostId){this.hostId = hostId;}

    public BigDecimal getTotal() {
//        BigDecimal total = new BigDecimal(0);
//        if (host == null || host.getStandardRate()==null || host.getWeekendRate()==null){
//            return BigDecimal.ZERO;
//        }
//        while (startDate != endDate){
//            if(startDate.getDayOfWeek()== DayOfWeek.FRIDAY || startDate.getDayOfWeek() == DayOfWeek.SATURDAY){
//                total = total.add(host.getWeekendRate());
//                startDate = startDate.plusDays(1);
//            }
//            else {
//                total = total.add(host.getStandardRate());
//                startDate = startDate.plusDays(1);
//            }
//        }

        return total;
    }

    public void setTotal(BigDecimal total){
        this.total = total;
    }

}
