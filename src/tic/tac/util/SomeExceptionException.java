package tic.tac.util;

public class SomeExceptionException extends UnsupportedOperationException {

    public SomeExceptionException() {
        super();
    }

    public SomeExceptionException(String message) {
        super(message);
    }

    public SomeExceptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SomeExceptionException(Throwable cause) {
        super(cause);
    }
}
