package mk.ukim.finki.ecimer.web.exceptionhandling;

public class ExceptionResponse {
    private String message;
    private String code;
    private Object errors;

    public ExceptionResponse() {

    }

    public ExceptionResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public ExceptionResponse(String message, String code, Object errors) {
        this.message = message;
        this.code = code;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }
}
