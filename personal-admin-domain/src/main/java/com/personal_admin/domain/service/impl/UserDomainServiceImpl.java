package com.personal_admin.domain.service.impl;

import com.personal_admin.domain.model.Credentials;
import com.personal_admin.domain.model.entity.User;
import com.personal_admin.domain.repository.UserRepository;
import com.personal_admin.domain.service.UserDomainService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserDomainServiceImpl implements UserDomainService {
    @Resource
    private UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    public boolean validateCredentials(String password, String passwordHash) {
        return this.userRepository.validateCredentials(password, passwordHash);
    }

    @Override
    public boolean register(Credentials credentials) {
        return this.userRepository.register(credentials);
    }
}
