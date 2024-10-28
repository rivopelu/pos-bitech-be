package com.pos.service;

import com.pos.entities.Account;
import com.pos.request.RequestCreateAccount;
import com.pos.response.ResponseCreateNewAccount;

public interface AccountService {

    ResponseCreateNewAccount createNewAccount(RequestCreateAccount req);

    String createAvatar(String name);

    Account getCurrentAccount();

    Account getCurrentAccount(String id);

    String getCurrentAccountId();

}
