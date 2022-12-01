package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static am.itspace.townrestaurantsweb.utilWeb.PageUtil.getTotalPages;

@Controller
@RequiredArgsConstructor
@RequestMapping("/baskets")
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/add/{id}")
    public String addBasket(@PathVariable("id") int id,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        try {
            basketService.addBasket(id, currentUser);
            return "redirect:/baskets";
        } catch (IllegalStateException e) {
            return "index";
        }
    }

    @GetMapping
    public String baskets(@RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "size", defaultValue = "5") int size,
                          @AuthenticationPrincipal CurrentUser currentUser,
                          ModelMap modelMap) {
        try {
            Page<BasketOverview> baskets = basketService.getBaskets(PageRequest.of(page, size), currentUser.getUser());
            modelMap.addAttribute("baskets", baskets);
            List<Integer> pageNumbers = getTotalPages(baskets);
            modelMap.addAttribute("pageNumbers", pageNumbers);
            return "baskets";
        } catch (IllegalStateException e) {
            return "redirect:/users/home";
        }
    }
//    @GetMapping("/add")
//    public String addBasketPage() {
//        return "addBasket";
//    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, ModelMap modelMap) {
        try { //ka basket,
            basketService.delete(id);
            return "redirect:/baskets";
        } catch (IllegalStateException e) {
            modelMap.addAttribute("errorMessageId", "Something went wrong, Try again!");
            return "baskets";
        }
    }
}
