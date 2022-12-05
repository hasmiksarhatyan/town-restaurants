package am.itspace.townrestaurantsrest.exception;

public class VerificationException extends BaseException {

    public VerificationException(Error error) {
        super(error);
    }
}
