package com.personal_admin.application.mapper;

import com.personal_admin.application.model.response.LoginResponse;
import com.personal_admin.domain.model.Token;
import org.springframework.beans.BeanUtils;

public class LoginMapper {
    public static LoginResponse mapperToLoginResponse(Token token) {
        if (token == null) {
            return null;
        }
        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(token, loginResponse);
        return loginResponse;
    }
}
