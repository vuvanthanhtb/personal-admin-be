package com.personal_admin.application.service;

import com.personal_admin.domain.model.Credentials;
import com.personal_admin.domain.model.Token;

public interface AuthApplicationService {
    Token authenticate(Credentials credentials);

    Token refreshToken(String refreshTokenRequest);

    boolean register(Credentials credentials);
}
