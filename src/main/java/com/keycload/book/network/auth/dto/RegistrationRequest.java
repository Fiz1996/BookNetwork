package com.keycload.book.network.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @Email(message = "Invalid email")
    @NotEmpty(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "password must be between 2 and 20 characters")
    private String password;
    @NotEmpty(message = "FirstName is required")
    @NotBlank(message = "FirstName is required")
    private String firstName;
    @NotEmpty(message = "LastName is required")
    @NotBlank(message = "LastName is required")
    private String lastName;

}
