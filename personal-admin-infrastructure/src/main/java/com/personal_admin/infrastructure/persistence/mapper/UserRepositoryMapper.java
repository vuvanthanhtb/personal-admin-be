package com.personal_admin.infrastructure.persistence.mapper;

import com.personal_admin.domain.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryMapper extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
