package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantsweb.serviceWeb.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReserveController {

    private final ReserveService reserveService;
}
