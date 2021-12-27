package me.braun.identityservice.api.dto.jwt;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class JwtRequestDto {
    @Getter
    private String  email;
    @Getter
    private String password;
}
