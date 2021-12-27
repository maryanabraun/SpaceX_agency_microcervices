package me.braun.missionservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountDto {

    @NotBlank
    @Length(max = 50)
    private String firstName;

    @NotBlank
    @Length(max = 50)
    private String lastName;

    @NotBlank
    @Length(max = 255)
    @Email
    private String email;

    @Length(max = 50)
    private String phone;

    @Builder.Default
    private Long roleId = 1L;
    private Boolean enabled;


}