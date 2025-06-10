package com.personal_admin.application.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
