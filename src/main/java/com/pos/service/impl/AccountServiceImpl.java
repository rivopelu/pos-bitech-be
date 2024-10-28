package com.pos.service.impl;

import com.pos.entities.Account;
import com.pos.entities.repositories.AccountRepository;
import com.pos.enums.ResponseEnum;
import com.pos.exception.BadRequestException;
import com.pos.exception.NotFoundException;
import com.pos.exception.SystemErrorException;
import com.pos.request.RequestCreateAccount;
import com.pos.response.ResponseCreateNewAccount;
import com.pos.service.AccountService;
import com.pos.utils.AuthConstant;
import com.pos.utils.EntityUtils;
import com.pos.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest httpServletRequest;

    @Override
    public ResponseCreateNewAccount createNewAccount(RequestCreateAccount req) {
        boolean checkExistAccount = accountRepository.existsAccountByUsernameAndActiveTrue(req.getUsername());
        if (checkExistAccount) {
            throw new BadRequestException(ResponseEnum.ACCOUNT_ALREADY_EXIST);
        }
        String generatePassword = StringUtils.generateRandomString();
        String password = passwordEncoder.encode(generatePassword);
        String avatar= createAvatar(req.getName());
        Account account = Account.builder()
                .name(req.getName())
                .username(req.getUsername())
                .password(password)
                .role(req.getRole())
                .avatar(avatar)
                .build();
        EntityUtils.created(account, getCurrentAccountId());
        try {

            accountRepository.save(account);
            return ResponseCreateNewAccount.builder()
                    .name(account.getName())
                    .password(generatePassword)
                    .username(account.getUsername())
                    .name(account.getName())
                    .avatar(account.getAvatar())
                    .build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }


    @Override
    public String createAvatar(String name) {
        return "https://ui-avatars.com/api/?background=random&name=" + name;
    }

    public Account getCurrentAccount() {
        String currentUserId = httpServletRequest.getAttribute(AuthConstant.HEADER_X_WHO).toString();
        Optional<Account> account = accountRepository.findById(currentUserId);
        return account.orElse(null);
    }

    @Override
    public Account getCurrentAccount(String id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new NotFoundException(ResponseEnum.ACCOUNT_NOT_FOUND.name());
        }
        return account.get();
    }

    @Override
    public String getCurrentAccountId() {
        return httpServletRequest.getAttribute(AuthConstant.HEADER_X_WHO) != null ? httpServletRequest.getAttribute(AuthConstant.HEADER_X_WHO).toString() : null;

    }
}
