package am.itspace.townrestaurantsrest.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {

    USER_REGISTRATION_FAILED(4091, HttpStatus.CONFLICT, "Email already exists"),

    USER_NOT_FOUND(4011, HttpStatus.UNAUTHORIZED, "User not found"),

    RESTAURANT_NOT_FOUND(4041, HttpStatus.NOT_FOUND, "Restaurant not found"),

    RESTAURANT_ALREADY_EXISTS(4001, HttpStatus.BAD_REQUEST, "Restaurant already exists"),

    CATEGORY_NOT_FOUND(4042, HttpStatus.NOT_FOUND, "Restaurant category not found"),

    CATEGORY_ALREADY_EXISTS(4002, HttpStatus.BAD_REQUEST, "Restaurant category already exists"),

    TO_DO_NOT_FOUND(4043, HttpStatus.NOT_FOUND, "ToDo not found"),

    TO_DO_IS_NUll(4044, HttpStatus.NOT_FOUND, "ToDo is null"),

    PRODUCT_NOT_FOUND(4045, HttpStatus.NOT_FOUND, "Product not found"),

    PRODUCT_ALREADY_EXISTS(4003, HttpStatus.BAD_REQUEST, "Product already exists"),

    EVENT_NOT_FOUND(4046, HttpStatus.NOT_FOUND, "Event not found"),

    EVENT_ALREADY_EXISTS(4004, HttpStatus.BAD_REQUEST, "Event already exists");


    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}


