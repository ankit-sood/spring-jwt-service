package dev.ankis.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class LoginResponse {
    private String token;
    private long expiresIn;
}
