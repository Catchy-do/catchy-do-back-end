package com.main.catchy.utils.JwtResponce;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequest {

    private String refreshToken;

}