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
    public String verifyUserIsEnabled(Model theModel, IllegalStateException ex) {
        theModel.addAttribute("error", ex.getMessage());
        return "error";
    }

    @GetMapping
    @ExceptionHandler(value = IOException.class)
    public String getFile(Model theModel, IOException ex) {
        theModel.addAttribute("error", ex.getMessage());
        return "error";
    }

    @GetMapping
    @ExceptionHandler(value = MessagingException.class)
    public String sendEmail(Model theModel, MessagingException ex) {
        theModel.addAttribute("error", ex.getMessage());
        return "error";
    }
}
