package com.ust.Security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ResetPasswordDTO {
    @NotBlank
    private String token;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password must include at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String newPassword;
}
