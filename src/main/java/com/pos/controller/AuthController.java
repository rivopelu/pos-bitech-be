package com.pos.controller;

import com.pos.annotations.BaseController;
import com.pos.request.ReqRegisterSuperAdmin;
import com.pos.request.RequestSignIn;
import com.pos.response.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@BaseController("auth")
public interface AuthController {

    @PostMapping("v1/register-super-admin")
    BaseResponse registerSuperAdmin(@RequestBody ReqRegisterSuperAdmin reqRegisterSuperAdmin);

    @PostMapping("v1/sign-in/super-admin")
    BaseResponse signInSuperAdmin(@RequestBody RequestSignIn req);
}
