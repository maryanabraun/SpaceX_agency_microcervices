package me.braun.missionservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MissionDto {
    @NotBlank
    private String name;
    private String description;

    @NotNull
    private Long customerId;

    @Builder.Default
    @NotNull
    private Long spaceCraftId = 2L;

    @Builder.Default
    @NotNull
    private Short statusId = 1;
    @Builder.Default
    private Long curatorId = 8L;
    private Integer payloadWeight;

    @Builder.Default
    private Date date = new Date(System.currentTimeMillis());
    @NotNull
    private Integer duration;
    @NotNull
    private Byte serviceId;
    private Long missionPrice;
    private Long servicePrice;
}
