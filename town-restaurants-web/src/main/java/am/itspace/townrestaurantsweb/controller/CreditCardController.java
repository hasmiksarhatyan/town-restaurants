package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static am.itspace.townrestaurantsweb.utilWeb.PageUtil.getTotalPages;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @GetMapping
    public String creditCards(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @AuthenticationPrincipal CurrentUser currentUser,
                              ModelMap modelMap) {
        Page<CreditCardOverview> creditCards = creditCardService.getCreditCards(PageRequest.of(page, size), currentUser.getUser());
        modelMap.addAttribute("creditCards", creditCards);
        List<Integer> pageNumbers = getTotalPages(creditCards);
        modelMap.addAttribute("pageNumbers", pageNumbers);
        return "orders";
    }

    @PostMapping("/add")
    public String addCreditCard(@ModelAttribute CreateCreditCardDto dto,
                                @AuthenticationPrincipal CurrentUser currentUser) {
        creditCardService.addCreditCard(dto, currentUser.getUser());
        return "redirect:/orders";
    }
}
