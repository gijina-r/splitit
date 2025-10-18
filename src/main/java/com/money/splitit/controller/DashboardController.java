package com.money.splitit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public String redirectToLogin() {
        return "redirect:/login";
    }
}
