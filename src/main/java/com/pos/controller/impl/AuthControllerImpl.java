package com.pos.controller.impl;

import com.pos.annotations.BaseControllerImpl;
import com.pos.controller.AuthController;
import com.pos.request.ReqRegisterSuperAdmin;
import com.pos.request.RequestSignIn;
import com.pos.response.BaseResponse;
import com.pos.service.AuthService;
import com.pos.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;

@BaseControllerImpl
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public BaseResponse registerSuperAdmin(ReqRegisterSuperAdmin reqRegisterSuperAdmin) {
        return ResponseHelper.createBaseResponse(authService.registerSuperAdmin(reqRegisterSuperAdmin));
    }

    @Override
    public BaseResponse signInSuperAdmin(RequestSignIn req) {
        return ResponseHelper.createBaseResponse(authService.signInSuperAdmin(req));
    }
}
