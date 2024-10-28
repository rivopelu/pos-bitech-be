package com.pos.service;


import com.pos.enums.ResponseEnum;
import com.pos.request.ReqRegisterSuperAdmin;
import com.pos.request.RequestSignIn;
import com.pos.response.ResponseSignIn;

public interface AuthService {

    ResponseEnum registerSuperAdmin(ReqRegisterSuperAdmin reqRegisterSuperAdmin);

    ResponseSignIn signInSuperAdmin(RequestSignIn req);
}
