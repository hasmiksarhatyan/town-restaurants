package am.itspace.townrestaurantsrest.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {

    RESTAURANT_NOT_FOUND(4041, HttpStatus.NOT_FOUND, "Restaurant not found"),

    RESTAURANT_ALREADY_EXISTS(4001, HttpStatus.BAD_REQUEST, "Restaurant already exists"),

    RESTAURANT_CATEGORY_NOT_FOUND(4042, HttpStatus.NOT_FOUND, "Restaurant category not found"),

    RESTAURANT_CATEGORY_ALREADY_EXISTS(4002, HttpStatus.BAD_REQUEST, "Restaurant category already exists"),

    PRODUCT_NOT_FOUND(4043, HttpStatus.NOT_FOUND, "Product not found"),

    PRODUCT_ALREADY_EXISTS(4003, HttpStatus.BAD_REQUEST, "Product already exists"),

    EVENT_NOT_FOUND(4044, HttpStatus.NOT_FOUND, "Event not found"),

    EVENT_ALREADY_EXISTS(4004, HttpStatus.BAD_REQUEST, "Event already exists"),

    PRODUCT_CATEGORY_NOT_FOUND(4045, HttpStatus.NOT_FOUND, "Product category not found"),

    PRODUCT_CATEGORY_ALREADY_EXISTS(4005, HttpStatus.BAD_REQUEST, "Product category already exists"),

    UNAUTHORIZED(4011, HttpStatus.UNAUTHORIZED, "User not found"),

    USER_NOT_FOUND(4046, HttpStatus.NOT_FOUND, "User not found"),

    USER_REGISTRATION_FAILED(4006, HttpStatus.BAD_REQUEST, "Email already exists"),

    RESERVE_NOT_FOUND(4047, HttpStatus.NOT_FOUND, "Reserve not found"),

    RESERVE_ALREADY_EXISTS(4007, HttpStatus.BAD_REQUEST, "Reserve already exists"),

    BASKET_NOT_FOUND(4048, HttpStatus.NOT_FOUND, "Basket not found"),

    BASKET_ALREADY_EXISTS(4008, HttpStatus.BAD_REQUEST, "Basket already exists"),

    PROVIDED_WRONG_PASSWORD(4009, HttpStatus.BAD_REQUEST, "Provided wrong credentials to change the password"),

    PROVIDED_SAME_PASSWORD(4091, HttpStatus.CONFLICT, "Provided the same password to change password"),

    SEND_EMAIL_FAILED(5001, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send an email"),

    USER_ALREADY_ENABLED(4092, HttpStatus.CONFLICT, "User already enabled"),

    TOKEN_NOT_FOUND(4049, HttpStatus.NOT_FOUND, "Token not found"),

    TOKEN_EXPIRED(4093, HttpStatus.CONFLICT, "Token has expired"),

    NEEDS_AUTHENTICATION(4094, HttpStatus.CONFLICT, "Needs to authenticate"),

    FILE_NOT_FOUND(4050, HttpStatus.NOT_FOUND, "File not found"),

    FILE_UPLOAD_FAILED(4010, HttpStatus.BAD_REQUEST, "Error occurred while uploading multipart file.");


    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}


