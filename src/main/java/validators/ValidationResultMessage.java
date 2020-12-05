package validators;

public class ValidationResultMessage {
    private String message;
    private Status status;
    private String errorFieldName;





    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorFieldName() {
        return errorFieldName;
    }

    public void setErrorFieldName(String errorFieldName) {
        this.errorFieldName = errorFieldName;
    }
}
