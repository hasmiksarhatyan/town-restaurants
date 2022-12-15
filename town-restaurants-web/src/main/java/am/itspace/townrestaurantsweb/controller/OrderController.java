package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.EditOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.BasketService;
import am.itspace.townrestaurantsweb.serviceWeb.OrderService;
import am.itspace.townrestaurantsweb.validation.ErrorMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static am.itspace.townrestaurantsweb.utilWeb.PageUtil.getTotalPages;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final BasketService basketService;


    @GetMapping
    public String orders(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size,
                         ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser().getRole() == Role.MANAGER) {
            Page<OrderOverview> orders = orderService.getOrders(PageRequest.of(page - 1, size));
            modelMap.addAttribute("orders", orders);
            modelMap.addAttribute("pageNumbers", getTotalPages(orders));
        } else {
            Page<OrderOverview> ordersByUser = orderService.getOrdersByUser(currentUser.getUser().getId(), PageRequest.of(page - 1, size));
            modelMap.addAttribute("orders", ordersByUser);
            modelMap.addAttribute("pageNumbers", getTotalPages(ordersByUser));
        }
        modelMap.addAttribute("message", "You don't have any orders yet");
        return "orders";
    }

    @GetMapping("/{id}")
    public String order(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("order", orderService.getById(id));
        modelMap.addAttribute("productQuantity", orderService.getQuantity(id));
        return "order";
    }


    @GetMapping("/add")
    public String addOrderPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        List<BasketOverview> baskets = basketService.getBaskets(currentUser.getUser());
        if (baskets.size() != 0) {
            modelMap.addAttribute("baskets", basketService.getBaskets(currentUser.getUser()));
            modelMap.addAttribute("totalPrice", basketService.totalPrice(currentUser.getUser()));
            return "addOrder";
        } else return "redirect:/baskets";
    }


    @PostMapping("/add")
    public String addOrder(@ModelAttribute @Valid CreateOrderDto orderDto, BindingResult orderResult,
                           @ModelAttribute @Valid CreateCreditCardDto cardDto, BindingResult cardResult,
                           @AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {

        modelMap.addAttribute("baskets", basketService.getBaskets(currentUser.getUser()));
        modelMap.addAttribute("totalPrice", basketService.totalPrice(currentUser.getUser()));
        if (orderResult.hasErrors() || (orderDto.getPaymentOption().equals("CREDIT_CARD") && cardResult.hasErrors())) {
            Map<String, Object> orderErrors = ErrorMap.getErrorMessages(orderResult);
            modelMap.addAttribute("orderErrors", orderErrors);
            if (orderDto.getPaymentOption().equals("CREDIT_CARD")) {
                Map<String, Object> cardErrors = ErrorMap.getErrorMessages(cardResult);
                modelMap.addAttribute("cardErrors", cardErrors);
                log.info("Errors in addOrder method");
            }
            return "addOrder";
        }

        orderService.addOrder(orderDto, cardDto, currentUser.getUser());
        return "confirmOrder";
    }

    @GetMapping("/edit/{id}")
    public String editOrderPage(@PathVariable("id") int id, @ModelAttribute EditOrderDto dto, ModelMap modelMap) {
        modelMap.addAttribute("order", orderService.getById(id));
        modelMap.addAttribute("productQuantity", orderService.getQuantity(id));
        return "order";
    }

    @PostMapping("/edit/{id}")
    public String editOrder(@PathVariable("id") int id, @ModelAttribute EditOrderDto dto) {
        orderService.editOrder(dto, id);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, ModelMap modelMap) {
        try {
            orderService.delete(id);
            return "redirect:/orders";
        } catch (IllegalStateException e) {
            modelMap.addAttribute("errorMessageId", "Something went wrong, Try again!");
            return "orders";
        }
    }
}
