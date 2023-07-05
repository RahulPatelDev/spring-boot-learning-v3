package com.rahulpateldev.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
