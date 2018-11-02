package com.todcode.api.api.v1.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {
    private String username;
    private String password;
}
