package com.pos.service;


import com.pos.enums.ResponseEnum;
import com.pos.request.ReqRegisterSuperAdmin;

public interface AuthService {

    ResponseEnum registerSuperAdmin(ReqRegisterSuperAdmin reqRegisterSuperAdmin);
}
