package am.itspace.townrestaurantsrest.exception;

public class EntityAlreadyExistsException extends BaseException {

    public EntityAlreadyExistsException(Error error) {
        super(error);
    }
}
