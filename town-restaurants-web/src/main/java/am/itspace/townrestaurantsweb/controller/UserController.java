package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantscommon.dto.user.ChangePasswordDto;
import am.itspace.townrestaurantscommon.dto.user.CreateUserDto;
import am.itspace.townrestaurantscommon.dto.user.EditUserDto;
import am.itspace.townrestaurantscommon.dto.user.UserOverview;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.UserService;
import am.itspace.townrestaurantsweb.utilWeb.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static String ERROR;
    private static String ERROR2;

    @GetMapping
    public String users(@RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "5") int size,
                        ModelMap modelMap) {
        Page<UserOverview> users = userService.getUsers(PageRequest.of(page, size));
        modelMap.addAttribute("users", users);
        List<Integer> pageNumbers = PageUtil.getTotalPages(users);
        modelMap.addAttribute("pageNumbers", pageNumbers);
        return "users";
    }

    @GetMapping("/add")
    public String addUserPage() {
        return "addUser";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute CreateUserDto dto, ModelMap modelMap, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/addUser";
        }
        try {
            userService.saveUser(dto);
            return "redirect:/loginPage";
        } catch (IllegalStateException | MessagingException e) {
            modelMap.addAttribute("errorMessageEmail", "Email already in use");
            return "/addUser";
        }
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token) throws Exception {
        userService.verifyUser(token);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, ModelMap modelMap) {
        try {
            userService.delete(id);
            return "redirect:/users";
        } catch (IllegalStateException e) {
            modelMap.addAttribute("errorMessageId", "Something went wrong, Try again!");
            return "users";
        }
    }

    @GetMapping("/home")
    public String customerPage(@AuthenticationPrincipal CurrentUser currentUser,
                               ModelMap modelMap) {
        modelMap.addAttribute("errorMessageName", ERROR);
        modelMap.addAttribute("errorMessagePassword", ERROR2);
        ERROR = null;
        ERROR2 = null;
        User user = currentUser.getUser();
        if (user.getRole() == Role.CUSTOMER) {
            return "customer";
        } else if (user.getRole() == Role.MANAGER) {
            return "manager";
        } else if (user.getRole() == Role.RESTAURANT_OWNER) {
            return "restaurantOwner";
        }
        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute EditUserDto dto,
                       @PathVariable int id,
                       @AuthenticationPrincipal CurrentUser user) {
        try {
            userService.editUser(dto, id);
        } catch (IllegalStateException ex) {
            ERROR = ex.getMessage();
        }
        return "redirect:/users/home";
    }

    @PostMapping("/changePassword/{id}")
    public String changePassword(@PathVariable int id,
                                 @ModelAttribute ChangePasswordDto dto) {
        try {
            userService.changePassword(id, dto);
        } catch (IllegalStateException ex) {
            ERROR2 = ex.getMessage();
        }
        return "redirect:/users/home";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, ModelMap modelMap) {
        modelMap.addAttribute("userId", id);
        return "customerEdit";
    }

    @GetMapping("/eeee")
    public String editW(@PathVariable int id, ModelMap modelMap) {
        modelMap.addAttribute("userId", id);
        return "customerEdit";
    }
}
