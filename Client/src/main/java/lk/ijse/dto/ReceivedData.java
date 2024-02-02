package lk.ijse.dto;

public class ReceivedData {
    private String message;

    public ReceivedData() {
    }

    public ReceivedData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
