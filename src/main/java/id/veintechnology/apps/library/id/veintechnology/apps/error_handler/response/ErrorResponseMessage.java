package id.veintechnology.apps.library.id.veintechnology.apps.error_handler.response;

public class ErrorResponseMessage {
    private boolean success;
    private String message;

    public ErrorResponseMessage() {
        this.success = false;
        this.message = "Something went wrong !";
    }

    public ErrorResponseMessage(String message) {
        this.message = message;
        this.success = false;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
