package me.braun.missionservice.api.dto;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    String code;
    String description;
}
