package com.exe201.opalwed.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/test")
public class TestController {


    @GetMapping
    public String test() {
        return "only auth";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String test1() {
        return "only user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String test2() {
        return "only admin";
    }

}
