package am.itspace.townrestaurantsrest.controller;

import am.itspace.townrestaurantscommon.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class MyControllerAdvice {

//    public static User getUserDetails() {
//        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }
}
