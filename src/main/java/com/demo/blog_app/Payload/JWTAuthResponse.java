package com.demo.blog_app.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
    private String tokenType="Bearer";
    private String accessToken;

    public JWTAuthResponse(String token) {
        this.accessToken=token;
    }
}
