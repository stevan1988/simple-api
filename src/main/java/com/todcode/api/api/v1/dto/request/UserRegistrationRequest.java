package com.todcode.api.api.v1.dto.request;

import com.todcode.api.api.v1.dto.type.GanderType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserRegistrationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String eMail;
    private Date birth;
    private GanderType gander;
}
