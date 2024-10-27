package com.pos.service.impl;

import com.pos.entities.Account;
import com.pos.entities.repositories.AccountRepository;
import com.pos.enums.AccountRole;
import com.pos.enums.ResponseEnum;
import com.pos.exception.BadRequestException;
import com.pos.exception.SystemErrorException;
import com.pos.request.ReqRegisterSuperAdmin;
import com.pos.service.AuthService;
import com.pos.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseEnum registerSuperAdmin(ReqRegisterSuperAdmin reqRegisterSuperAdmin) {
        boolean existAccount = accountRepository.existsAccountByUsernameAndActiveTrue(reqRegisterSuperAdmin.getUsername());
        if (existAccount) {
            throw new BadRequestException(ResponseEnum.ACCOUNT_ALREADY_EXIST);
        }
        String password = passwordEncoder.encode(reqRegisterSuperAdmin.getPassword());

        try {
            Account account = Account.builder()
                    .name(reqRegisterSuperAdmin.getName())
                    .username(reqRegisterSuperAdmin.getUsername())
                    .password(password)
                    .role(AccountRole.SUPER_ADMIN)
                    .build();
            EntityUtils.created(account, "SYSTEM");
            accountRepository.save(account);
            return ResponseEnum.SUCCESS;
        } catch (Exception e) {
            throw new SystemErrorException();
        }
    }
}
