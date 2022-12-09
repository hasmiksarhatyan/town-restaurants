package am.itspace.townrestaurantsweb.exceptionHandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;
import java.io.IOException;

@ControllerAdvice
public class MyExceptionHandler {

    @GetMapping
    @ExceptionHandler(value = IllegalStateException.class)
    public String verifyUserIsEnabled(Model theModel) {
//        theModel.addAttribute("error",  "User already enabled");
        return "error";
    }

    @GetMapping
    @ExceptionHandler(value = IOException.class)
    public String getFile() {
        return "error";
    }

    @GetMapping
    @ExceptionHandler(value = MessagingException.class)
    public String sendEmail() {
        return "error";
    }
}
