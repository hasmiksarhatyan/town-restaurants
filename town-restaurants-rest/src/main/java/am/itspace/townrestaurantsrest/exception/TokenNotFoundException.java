package am.itspace.townrestaurantsrest.exception;

public class TokenNotFoundException extends BaseException {

    public TokenNotFoundException(Error error) {
        super(error);
    }
}
