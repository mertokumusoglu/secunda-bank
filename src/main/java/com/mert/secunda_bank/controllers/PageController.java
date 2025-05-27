package com.mert.secunda_bank.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

    @GetMapping("/")
    public String homepage() {
        return "Hello world";
    }
    @GetMapping("/about")
    public String aboutPage() {
        return "this is about page of our bank";
    }
    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "this is dashboard page of our users";
    }
}
