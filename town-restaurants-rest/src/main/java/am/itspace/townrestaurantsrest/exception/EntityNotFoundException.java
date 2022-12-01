package am.itspace.townrestaurantsrest.exception;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(Error error) {
        super(error);
    }
}
