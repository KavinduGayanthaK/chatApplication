package lk.ijse.dto;

public class TransferData {
    private String message;
    private String from;
    private String to;
    private String command;

    public TransferData(String message, String from, String to, String command) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.command = command;
    }

    public TransferData() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
