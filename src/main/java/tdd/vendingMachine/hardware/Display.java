package tdd.vendingMachine.hardware;

public class Display {

    private final String welcomeMessage;
    private String displayedMessage;

    public Display(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
        this.displayedMessage = welcomeMessage;
    }

    public void setMessage(String messageFormat, Object... args) {
        this.displayedMessage = String.format(messageFormat, args);
    }

    public String getMessage() {
        return displayedMessage;
    }

    public void clear() {
        this.displayedMessage = welcomeMessage;
    }
}
