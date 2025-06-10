package com.personal_admin.application.service.impl;

import com.personal_admin.application.service.AuthApplicationService;
import com.personal_admin.domain.model.Credentials;
import com.personal_admin.domain.model.Token;
import com.personal_admin.domain.model.entity.User;
import com.personal_admin.domain.service.UserDomainService;
import com.personal_admin.infrastructure.config.security.JwtService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthApplicationServiceImpl implements AuthApplicationService {
    @Resource
    private UserDomainService userDomainService;

    @Resource
    private JwtService jwtService;

    @Override
    public Token authenticate(Credentials credentials) {
        User foundUser = this.userDomainService.findUserByEmail(credentials.getEmail());
        if (foundUser == null) {
            return null;
        }

        boolean matchPassword = this.userDomainService.validateCredentials(credentials.getPassword(), foundUser.getPassword());

        if (matchPassword) {
            String accessToken = this.jwtService.generateAccessToken(foundUser.getEmail());
            String refreshToken = this.jwtService.generateRefreshToken(foundUser.getEmail());
            return new Token(accessToken, refreshToken);
        }

        return null;
    }

    @Override
    public Token refreshToken(String refreshToken) {
        if (this.jwtService.validateToken(refreshToken)) {
            String email = jwtService.getEmailFromToken(refreshToken);
            String newAccessToken = jwtService.generateAccessToken(email);
            return new Token(newAccessToken, refreshToken);
        }

        return null;
    }

    @Override
    public boolean register(Credentials credentials) {
        return this.userDomainService.register(credentials);
    }
}
