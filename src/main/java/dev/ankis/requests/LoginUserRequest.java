package dev.ankis.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginUserRequest {
    @NotBlank(message = "The email is mandatory.")
    @Email(message = "Invalid email address.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;
    @NotBlank(message = "The password is mandatory.")
    private String password;
}
