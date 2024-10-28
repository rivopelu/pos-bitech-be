package com.pos.controller;

import com.pos.annotations.BaseController;
import com.pos.request.RequestCreateClient;
import com.pos.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("client")
public interface ClientController {

    @PostMapping("v1/new")
    BaseResponse createClient(@RequestBody RequestCreateClient req);
}
