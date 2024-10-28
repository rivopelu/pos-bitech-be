package com.pos.service.impl;

import com.pos.entities.Account;
import com.pos.entities.repositories.AccountRepository;
import com.pos.enums.AccountRole;
import com.pos.enums.ResponseEnum;
import com.pos.exception.BadRequestException;
import com.pos.exception.SystemErrorException;
import com.pos.request.ReqRegisterSuperAdmin;
import com.pos.request.RequestSignIn;
import com.pos.response.ResponseSignIn;
import com.pos.service.AuthService;
import com.pos.service.JwtService;
import com.pos.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


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
            throw new SystemErrorException(e);
        }
    }

    @Override
    public ResponseSignIn signInSuperAdmin(RequestSignIn req) {
        Optional<Account> findAccount = accountRepository.findByUsernameAndActiveTrue(req.getUsername());
        if (findAccount.isEmpty()) {
            throw new BadRequestException(ResponseEnum.SIGN_IN_FAILED);
        }
        if (findAccount.get().getRole() != AccountRole.SUPER_ADMIN) {
            throw new BadRequestException(ResponseEnum.ACCESS_DENIED);
        }

        try {
            return getSignIn(findAccount.get(), req.getPassword());
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    private ResponseSignIn getSignIn(Account account, String password) {
        Authentication authentication;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);
        try {
            return ResponseSignIn.builder().token(jwt).build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
