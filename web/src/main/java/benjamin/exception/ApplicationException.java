package benjamin.exception;


public class ApplicationException extends RuntimeException {
    public ApplicationException(final String msg) {
        super(msg);
    }

    public ApplicationException(Exception e) {
        super(e);
    }
}
