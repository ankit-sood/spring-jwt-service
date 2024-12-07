package dev.ankis.requests;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class RegisterUserRequest {
    private String email;
    private String password;
    private String fullName;
}
