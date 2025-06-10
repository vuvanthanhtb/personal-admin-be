package com.personal_admin.domain.service;

import com.personal_admin.domain.model.Credentials;
import com.personal_admin.domain.model.entity.User;

public interface UserDomainService {
    User findUserByEmail(String email);

    boolean validateCredentials(String password, String passwordHash);

    boolean register(Credentials credentials);
}
