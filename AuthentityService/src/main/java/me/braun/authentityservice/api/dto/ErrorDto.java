package me.braun.authentityservice.api.dto;

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
