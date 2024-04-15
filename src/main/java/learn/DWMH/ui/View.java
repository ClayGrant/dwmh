package learn.DWMH.ui;

import org.springframework.stereotype.Component;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import learn.DWMH.models.Host;
import learn.DWMH.models.Guest;
import learn.DWMH.models.Reservation;

@Component
public class View {

    private final ConsoleIO io;
    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            io.printf("%s. %s%n", option.getValue(), option.getTitle());

            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public String getEmail() {return io.readRequiredString("Enter email: "); }

    public LocalDate getDate() {return io.readLocalDate("Enter in MM/dd/yyyy format: "); }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    public int getGuest(List<Guest> guest) {
        if (guest.size() == 0) {
            io.println("No guests found.");
            return 0;
        }
        return guest.get(0).getId();
    }

    public String getHost(List<Host> hosts){
        if (hosts.size() == 0){
            io.println("No host found.");
            return null;
        }

        return hosts.get(0).getId();
    }

    public Reservation chooseReservation(List<Reservation> reservations){
        if (reservations.size() == 0){
            return null;
        }

        displayReservations(reservations);
        io.println("0: Exit");
        String message = String.format("Select reservation [0-%s]: ", reservations.size());

        int index = io.readInt(message, 0, reservations.size());
        int reservationId = reservations.get(index-1).getId();
        Reservation reservation = reservations.stream().filter(
                        r -> r.getId() == reservationId)
                .findFirst().orElse(null);
        if (reservation == null) {
            displayStatus(false, String.format(
                    "No reservation with ID %s found for this host.", reservationId));
        }
        return reservations.get(index - 1);
    }

    public Reservation addReservation(int guestId, String hostId, Guest guest, Host host){
        displayHeader("Start date: ");
        LocalDate start = getDate();
        displayHeader("End date: ");
        LocalDate end = getDate();

        Reservation reservation = new Reservation();
        reservation.setStartDate(start);
        reservation.setEndDate(end);
        reservation.setHostId(hostId);
        reservation.setGuestId(guestId);
        reservation.setHost(host);
        reservation.setGuest(guest);

        return reservation;
    }

    public void update(Reservation reservation){
        if (reservation.getStartDate().isBefore(LocalDate.now())){
            displayHeader("Error: Reservation cannot be in the past.");
            return;
        }
        LocalDate start = getDate();
        reservation.setStartDate(start);
        LocalDate end = getDate();
        reservation.setEndDate(end);
    }

    public boolean delete(Reservation reservation){
        displayHeader("Confirmation:");
        io.printf("Reservation ID: %s, Guest ID: %s, %s - %s, $%s%n",
                reservation.getId(), reservation.getGuestId(), reservation.getStartDate(), reservation.getEndDate(),
                reservation.getTotal().setScale(2, RoundingMode.HALF_UP));
        String confirm = "Are you sure you want to cancel this reservation? [y/n]";
        return io.readBoolean(confirm);
    }


    public void displayReservations(List<Reservation> reservations) {
        if (reservations.size() == 0) {
            io.println("No reservations.");
        }
        reservations.sort(Comparator.comparing(Reservation::getStartDate));
        int index = 1;
        for (Reservation r : reservations) {
            io.printf("%s: Reservation ID: %s, Guest ID: %s, %s - %s, $%s%n",
                    index++, r.getId(), r.getGuestId(), r.getStartDate(), r.getEndDate(),
                    r.getTotal().setScale(2, RoundingMode.HALF_UP));
        }
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }
}
