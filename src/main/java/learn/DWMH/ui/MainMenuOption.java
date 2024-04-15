package learn.DWMH.ui;

public enum MainMenuOption {

    VIEW(1, "View Reservations"),
    MAKE(2, "Make a New Reservation"),
    EDIT(3, "Edit an Existing Reservation"),
    CANCEL(4, "Cancel a Future Reservation"),
    EXIT(5, "Exit");

    private int value;
    private String title;

    public int getValue() {return value;}
    public String getTitle() {return title;}

    private MainMenuOption(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

}
