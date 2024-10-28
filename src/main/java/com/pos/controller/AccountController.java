package com.pos.controller;

import com.pos.annotations.BaseController;
import com.pos.request.RequestCreateAccount;
import com.pos.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("account")
public interface AccountController {

    @PostMapping("v1/create-account")
    BaseResponse createNewAccount(@RequestBody RequestCreateAccount req);

}
