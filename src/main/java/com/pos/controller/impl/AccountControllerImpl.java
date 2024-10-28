package com.pos.controller.impl;

import com.pos.annotations.BaseControllerImpl;
import com.pos.controller.AccountController;
import com.pos.request.RequestCreateAccount;
import com.pos.response.BaseResponse;
import com.pos.service.AccountService;
import com.pos.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;

@BaseControllerImpl
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    public BaseResponse createNewAccount(RequestCreateAccount req) {
        return ResponseHelper.createBaseResponse(accountService.createNewAccount(req));
    }
}
