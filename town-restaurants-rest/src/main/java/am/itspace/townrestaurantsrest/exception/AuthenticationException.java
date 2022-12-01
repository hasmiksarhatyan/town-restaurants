package am.itspace.townrestaurantsrest.exception;

public class AuthenticationException extends BaseException {

    public AuthenticationException(Error error) {
        super(error);
    }
}
