package am.itspace.townrestaurantsweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false) String error, ModelMap modelMap) {
        if (error != null && error.equals("true")) {
            modelMap.addAttribute("error", true);
        }
        return "loginPage";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}