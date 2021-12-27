package me.braun.identityservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    @NotBlank
    @Length(min = 3, max = 50)
    private String firstName;

    @NotBlank
    @Length(min = 3, max = 50)
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Length(min = 10, max = 14)
    @NumberFormat(pattern = "+############")
    private String phone;

    @NotBlank
    @Length(min = 4, max = 15)
    private String password;


}
