package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.payment.CreatePaymentDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.Order;
import am.itspace.townrestaurantscommon.entity.OrderStatus;
import am.itspace.townrestaurantscommon.entity.PaymentOption;
import am.itspace.townrestaurantscommon.repository.PaymentRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.BasketService;
import am.itspace.townrestaurantsweb.serviceWeb.OrderService;
import am.itspace.townrestaurantsweb.serviceWeb.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static am.itspace.townrestaurantsweb.utilWeb.PageUtil.getTotalPages;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final BasketService basketService;

    @GetMapping
    public String payments(@RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "5") int size,
                           ModelMap modelMap) {
        Page<PaymentOverview> payments = paymentService.getPayments(PageRequest.of(page, size));
        modelMap.addAttribute("payments", payments);
        List<Integer> pageNumbers = getTotalPages(payments);
        modelMap.addAttribute("pageNumbers", pageNumbers);
        return "payments";
    }

//    @GetMapping("/add")
//    public String addPaymentPage() {
//        return "addPayment";
//    }

    @PostMapping("/add")
    public String addPayment(@ModelAttribute CreatePaymentDto createPaymentDto,
                             @ModelAttribute CreateCreditCardDto creditCardDto,
                             @AuthenticationPrincipal CurrentUser currentUser,
                             ModelMap modelMap) {
        PaymentOption option = createPaymentDto.getPaymentOption();
        if (option == PaymentOption.CREDIT_CARD) {
            try {
                paymentService.addPayment(createPaymentDto, creditCardDto, currentUser.getUser());
                modelMap.addAttribute("message", "Your purchase has been confirmed!");
                return "products";
            } catch (IllegalStateException e) {
                modelMap.addAttribute("messageError", "Wrong or Expired Credit Card");
                return "products";
            }
        } else {
            orderService.addOrder(Order.builder()
                    .orderAt(LocalDateTime.now())
                    .isPaid(false)
                    .status(OrderStatus.NEW)
                    .user(currentUser.getUser())
                    .totalPrice(createPaymentDto.getTotalPrice())
                    .build());
            modelMap.addAttribute("message", "Your purchase has been confirmed!");
            return "products";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, ModelMap modelMap) {
        try {
            paymentService.delete(id);
            return "redirect:/payments";
        } catch (IllegalStateException e) {
            modelMap.addAttribute("errorMessageId", "Something went wrong, Try again!");
            return "payments";
        }
    }
}
