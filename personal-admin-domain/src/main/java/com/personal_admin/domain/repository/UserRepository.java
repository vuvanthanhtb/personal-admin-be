package com.personal_admin.domain.repository;

import com.personal_admin.domain.model.Credentials;
import com.personal_admin.domain.model.entity.User;

public interface UserRepository {
    User findUserByEmail(String email);

    boolean validateCredentials(String password, String passwordHash);

    boolean register(Credentials credentials);
}
