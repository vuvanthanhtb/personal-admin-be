package com.personal_admin.controller.http;

import com.personal_admin.application.mapper.LoginMapper;
import com.personal_admin.application.model.request.LoginRequest;
import com.personal_admin.application.model.request.RefreshTokenRequest;
import com.personal_admin.application.model.response.LoginResponse;
import com.personal_admin.application.service.AuthApplicationService;
import com.personal_admin.controller.model.vo.ResultMessage;
import com.personal_admin.domain.model.Credentials;
import com.personal_admin.domain.model.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Resource
    private AuthApplicationService authApplicationService;

    @Operation(summary = "User login", description = "Authenticate user and return access and refresh tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResultMessage<LoginResponse> login(@RequestBody LoginRequest request) {
        Credentials credentials = new Credentials(request.getEmail(), request.getPassword());
        Token token = authApplicationService.authenticate(credentials);
        ResultMessage<LoginResponse> response = new ResultMessage<>();
        if (token == null) {
            response.setCode("AUTH_LOGIN_FAILED");
            response.setMessage("Username/Password invalid");
            response.setResult(null);
            response.setSuccess(Boolean.FALSE);
            return response;
        }
        response.setCode("AUTH_LOGIN_SUCCESS");
        response.setMessage("Login success");
        response.setResult(LoginMapper.mapperToLoginResponse(token));
        return response;
    }

    @Operation(summary = "Refresh token", description = "Generate new access token using refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed"),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token")
    })
    @PostMapping("/refresh")
    public ResultMessage<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        Token token = authApplicationService.refreshToken(request.getRefreshToken());
        ResultMessage<LoginResponse> response = new ResultMessage<>();

        if (token == null) {
            response.setCode("AUTH_REFRESH_TOKEN_FAILED");
            response.setMessage("Refresh token failed");
            response.setResult(null);
            response.setSuccess(Boolean.FALSE);
            return response;
        }
        response.setCode("AUTH_REFRESH_TOKEN_SUCCESS");
        response.setMessage("Refresh token success");
        response.setResult(LoginMapper.mapperToLoginResponse(token));
        return response;
    }

    @Operation(summary = "User registration", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "AUTH_REGISTER_SUCCESS", description = "User created successfully"),
            @ApiResponse(responseCode = "AUTH_REGISTER_FAIL", description = "Registration failed")
    })
    @PostMapping("/register")
    public ResultMessage<String> register(@RequestBody LoginRequest request) {
        Credentials credentials = new Credentials(request.getEmail(), request.getPassword());
        boolean isRegister = authApplicationService.register(credentials);
        ResultMessage<String> response = new ResultMessage<>();
        if (!isRegister) {
            response.setCode("AUTH_REGISTER_FAIL");
            response.setMessage("Register failed");
            response.setSuccess(Boolean.FALSE);
            return response;
        }
        response.setCode("AUTH_REGISTER_SUCCESS");
        response.setMessage("Register success");
        return response;
    }
}
