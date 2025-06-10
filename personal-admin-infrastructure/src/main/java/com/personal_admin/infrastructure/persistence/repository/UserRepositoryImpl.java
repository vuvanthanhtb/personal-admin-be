package com.personal_admin.infrastructure.persistence.repository;

import com.personal_admin.domain.model.Credentials;
import com.personal_admin.domain.model.entity.User;
import com.personal_admin.domain.repository.UserRepository;
import com.personal_admin.infrastructure.persistence.mapper.UserRepositoryMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserRepositoryImpl implements UserRepository {
    @Resource
    private UserRepositoryMapper userRepositoryMapper;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return this.userRepositoryMapper.findUserByEmail(email);
    }

    @Override
    public boolean validateCredentials(String password, String passwordHash) {
        return this.bCryptPasswordEncoder.matches(password, passwordHash);
    }

    @Override
    public boolean register(Credentials credentials) {
        try {
            User user = new User();
            user.setEmail(credentials.getEmail());
            user.setPassword(this.bCryptPasswordEncoder.encode(credentials.getPassword()));
            this.userRepositoryMapper.save(user);

            return true;
        } catch (Exception e) {
            log.error("Register failed  -> {}", e.getMessage());
            return false;
        }
    }
}
