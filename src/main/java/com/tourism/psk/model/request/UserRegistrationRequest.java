package com.tourism.psk.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    private String email;
    private String fullname;
    private String username;
    private String password;
}