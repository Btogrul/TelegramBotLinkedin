package com.ltc.telegrambotlinkedin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class SecurityTestController {

    @GetMapping("/public/get")
    public String publicGetMethod () {
        return "public get method";
    }

    @PostMapping("/public/post")
    public String publicPostMethod () {
        return "public post method";
    }

    @GetMapping("/admin/get")
    public String adminGetMethod () {
        return "admin get method";
    }

    @PostMapping("/admin/post")
    public String adminPostMethod () {
        return "admin post method";
    }

    @GetMapping("/user/get")
    public String userGetMethod () {
        return "user get method";
    }

    @PostMapping("/user/post")
    public String userPostMethod () {
        return "user post method";
    }
}
