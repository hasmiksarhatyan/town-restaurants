package am.itspace.townrestaurantsweb.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

@Component
public class ErrorMap {

    public static Map<String, Object> getErrorMessages(BindingResult bindingResult) {
        Map<String, Object> model = bindingResult.getModel();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            model.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return model;
    }
}
