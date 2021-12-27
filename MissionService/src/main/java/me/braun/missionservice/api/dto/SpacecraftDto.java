package me.braun.missionservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SpacecraftDto {
    private Byte nameId;
    private Double capacity;
    private Integer maxWeight;
    private Integer launchPrice;
}
