package com.example.jobsrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserRequest {

    @NotEmpty(message = "name should not be empty")
    private String name;
    @NotEmpty(message = "surname should not be empty")
    private String surname;
    @Email
    private String email;
    private String phone;
    private String city;
    @NotEmpty(message = "password should not be empty")
    private String password;
    private String picUrl;
    private String token;
}
