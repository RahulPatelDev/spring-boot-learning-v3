package com.rahulpateldev.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.rahulpateldev.model.entity.Roles;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class UserRequest {
    private String name;
    private String username;
    private String email;
    private String password;
    private Integer age;
    private List<Roles> roles;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
