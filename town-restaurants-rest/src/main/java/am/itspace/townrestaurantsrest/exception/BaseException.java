package am.itspace.townrestaurantsrest.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseException extends RuntimeException {

    protected final Error error;

    public BaseException(Error error) {
        this.error = error;
    }
}
