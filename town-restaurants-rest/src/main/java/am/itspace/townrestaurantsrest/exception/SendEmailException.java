package am.itspace.townrestaurantsrest.exception;

public class SendEmailException extends BaseException {

    public SendEmailException(Error error) {
        super(error);
    }
}
