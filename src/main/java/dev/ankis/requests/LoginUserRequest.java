package dev.ankis.requests;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginUserRequest {
    private String email;
    private String password;
}
