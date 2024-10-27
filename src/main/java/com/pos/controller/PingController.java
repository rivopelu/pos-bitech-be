package com.pos.controller;

import com.pos.annotations.BaseController;
import org.springframework.web.bind.annotation.GetMapping;

@BaseController("ping")
public interface PingController {

    @GetMapping
    public String ping();
}
