package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.Basket;
import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.BasketService;
import am.itspace.townrestaurantsweb.serviceWeb.OrderService;
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
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final BasketService basketService;

    @GetMapping
    public String orders(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int size,
                         ModelMap modelMap) {
        Page<OrderOverview> orders = orderService.getOrders(PageRequest.of(page - 1, size));
        modelMap.addAttribute("orders", orders);
        List<Integer> pageNumbers = getTotalPages(orders);
        modelMap.addAttribute("pageNumbers", pageNumbers);
        return "orders";
    }

    @GetMapping("/add")
    public String addOrderPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<BasketOverview> baskets = basketService.getBaskets(currentUser.getUser());
        if (baskets.size()!=0) {
            modelMap.addAttribute("baskets", basketService.getBaskets(currentUser.getUser()));
            modelMap.addAttribute("totalPrice", basketService.totalPrice(currentUser.getUser()));
            return "addOrder";
        }
       else return "redirect:/baskets";
    }


    @PostMapping("/add")
    public String addOrder(@ModelAttribute CreateOrderDto orderDto, CreateCreditCardDto creditCardDto,
                           @AuthenticationPrincipal CurrentUser currentUser,
                           ModelMap modelMap) {

        orderService.addOrder(orderDto, creditCardDto, currentUser.getUser());
        return "orders";
    }
}
