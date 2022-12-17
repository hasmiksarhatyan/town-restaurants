package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantscommon.dto.payment.EditPaymentDto;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import static am.itspace.townrestaurantsweb.utilWeb.PageUtil.getTotalPages;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public String payments(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size,
                           ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {

        if (currentUser.getUser().getRole() == Role.MANAGER) {
            Page<PaymentOverview> payments = paymentService.getPayments(PageRequest.of(page - 1, size));
            modelMap.addAttribute("payments", payments);
            modelMap.addAttribute("pageNumbers", getTotalPages(payments));
        } else {
            Page<PaymentOverview> paymentsByUser = paymentService.getPaymentsByUser(currentUser.getUser().getId(), PageRequest.of(page - 1, size));
            modelMap.addAttribute("payments", paymentsByUser);
            modelMap.addAttribute("pageNumbers", getTotalPages(paymentsByUser));
        }
        return "payments";
    }

    @GetMapping("/{id}")
    public String payment(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("payment", paymentService.getById(id));
        return "payment";
    }

    @GetMapping("/edit/{id}")
    public String editPaymentPage(@PathVariable("id") int id, @ModelAttribute EditPaymentDto dto, ModelMap modelMap) {
        modelMap.addAttribute("payment", paymentService.getById(id));
        return "payment";
    }

    @PostMapping("/edit/{id}")
    public String editPayment(@PathVariable("id") int id, @ModelAttribute EditPaymentDto dto) {
        paymentService.editPayment(dto, id);
        return "redirect:/payments";
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
