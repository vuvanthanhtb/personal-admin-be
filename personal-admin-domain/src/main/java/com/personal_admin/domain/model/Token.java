package com.personal_admin.domain.model;

import lombok.Value;

@Value
public class Token {
    String accessToken;
    String refreshToken;
}
