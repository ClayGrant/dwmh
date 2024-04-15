package learn.DWMH.ui;

import org.springframework.stereotype.Component;
import java.util.List;

import learn.DWMH.data.DataException;
import learn.DWMH.domain.GuestService;
import learn.DWMH.domain.ReservationService;
import learn.DWMH.domain.HostService;
import learn.DWMH.domain.Result;
import learn.DWMH.models.Guest;
import learn.DWMH.models.Host;
import learn.DWMH.models.Reservation;

@Component
public class Controller {

    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("DON'T WRECK MY HOUSE");
        try {
            runAppMenu();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye, Admin");
    }

    private void runAppMenu() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW:
                    viewReservations();
                    break;
                case MAKE:
                    makeReservation();
                    break;
                case EDIT:
                    editReservation();
                    break;
                case CANCEL:
                    cancelReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void viewReservations() throws DataException{

        view.displayHeader("Host Email: ");
        String email = view.getEmail();
        List<Host> hosts = hostService.findByHostEmail(email);
        String hostId = null;

        if (hosts.size() == 0){
            view.displayHeader("No hosts found");
            return;
        }
        else {
            hostId = hosts.get(0).getId();
        }

        List<Reservation> reservations = reservationService.findByHostId(hostId);

        view.displayHeader("Reservations");
        view.displayReservations(reservations);
        view.enterToContinue();

    }

    private void makeReservation() throws DataException{
        view.displayHeader("Guest Email: ");
        String guestEmail = view.getEmail();
        List<Guest> guests = guestService.findByGuestEmail(guestEmail);
        int guestId = view.getGuest(guests);
        if (guestId == 0){return;}

        view.displayHeader("Host Email: ");
        String hostEmail = view.getEmail();
        List<Host> hosts = hostService.findByHostEmail(hostEmail);
        String hostId = view.getHost(hosts);
        if (hostId == null){return;}

//        List<Reservation> reservations = reservationService.findByHostId(hostId);
//        view.displayHeader("Reservations");
//        view.displayReservations(reservations);
//        view.enterToContinue();
        Host host = hosts.get(0);
        Guest guest = guests.get(0);

        Reservation reservation = view.addReservation(guestId, hostId, guest, host);
        Result<Reservation> result = reservationService.add(reservation);

        if (!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        }
        else {
            String success = String.format("Reservation ID %s with total cost $%s created.", result.getPayload().getId(), reservation.getTotal());
            view.displayStatus(true, success);
        }
    }

    private void editReservation() throws DataException{
        view.displayHeader("Host Email: ");
        String email = view.getEmail();
        List<Host> hosts = hostService.findByHostEmail(email);
        String hostId = null;

        if (hosts.size() == 0){
            view.displayHeader("No hosts found");
            return;
        }
        else {
            hostId = hosts.get(0).getId();
        }

        List<Reservation> reservations = reservationService.findByHostId(hostId);

        view.displayHeader("Reservations");
        view.displayReservations(reservations);

        Reservation reservation = view.chooseReservation(reservations);
        reservation.setHostId(hostId);

        view.update(reservation);
        Result<Reservation> result = reservationService.update(reservation);

        if (!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        }
        else {
            String success = String.format("Reservation ID %s with total cost $%s updated.", result.getPayload().getId(), reservation.getTotal());
            view.displayStatus(true, success);
        }
    }

    private void cancelReservation() throws DataException{

        view.displayHeader("Host Email: ");
        String email = view.getEmail();
        List<Host> hosts = hostService.findByHostEmail(email);
        String hostId = null;

        if (hosts.size() == 0){
            view.displayHeader("No hosts found");
            return;
        }
        else {
            hostId = hosts.get(0).getId();
        }

        List<Reservation> reservations = reservationService.findByHostId(hostId);

        view.displayHeader("Reservations");
        view.displayReservations(reservations);

        Reservation reservation = view.chooseReservation(reservations);
        reservation.setHostId(hostId);

        Result<Reservation> result;
        if (view.delete(reservation)){
            result = reservationService.delete(reservation);
        }
        else {
            return;
        }

        if (!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        }
        else {
            String success = String.format("Reservation ID %s cancelled.", result.getPayload().getId());
            view.displayStatus(true, success);
        }
    }
}
