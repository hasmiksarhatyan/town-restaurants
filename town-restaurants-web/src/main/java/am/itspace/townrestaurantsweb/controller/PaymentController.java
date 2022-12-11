package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantsweb.serviceWeb.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public String payments(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "5") int size,
                           ModelMap modelMap) {
        Page<PaymentOverview> payments = paymentService.getPayments(PageRequest.of(page - 1, size));
        modelMap.addAttribute("payments", payments);
        List<Integer> pageNumbers = getTotalPages(payments);
        modelMap.addAttribute("pageNumbers", pageNumbers);
        return "payments";
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
