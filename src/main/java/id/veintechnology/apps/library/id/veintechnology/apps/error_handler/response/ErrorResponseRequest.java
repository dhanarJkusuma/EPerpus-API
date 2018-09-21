package id.veintechnology.apps.library.id.veintechnology.apps.error_handler.response;

import java.util.List;
import java.util.Map;

public class ErrorResponseRequest {
    private boolean success;
    private String message;
    private Map<String, List<String>> errors;

    public ErrorResponseRequest() {
        this.success = false;
    }

    public ErrorResponseRequest(String message) {
        this.message = message;
        this.success = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
