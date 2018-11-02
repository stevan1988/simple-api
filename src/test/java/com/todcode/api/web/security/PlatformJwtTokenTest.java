package com.todcode.api.web.security;

import com.todcode.api.api.v1.dto.request.UserLoginRequest;
import com.todcode.api.api.v1.dto.request.UserRegistrationRequest;
import com.todcode.api.api.v1.service.AuthenticationService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlatformJwtTokenTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/test")).andExpect(status().isUnauthorized());
    }

    @Test
    @Ignore
    public void shouldGenerateAuthToken() throws Exception {
        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .firstName("John")
                .lastName("Smith")
                .birth(new Date())
                .username("john123")
                .password("smith123")
                .build();
        authenticationService.signUp(userRegistrationRequest);
        ResponseEntity<?> response = authenticationService.login(UserLoginRequest.builder().username("john123").password("smith123").build());
        //        String token = TokenAuthenticationService.createToken("john");
        assertNotNull(response.getHeaders());
        mvc.perform(MockMvcRequestBuilders.get("/test").header("Authorization", response.getHeaders())).andExpect(status().isOk());
    }
}
