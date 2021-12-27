package me.braun.missionservice.api.controller;

import lombok.RequiredArgsConstructor;
import me.braun.missionservice.api.dto.AccountDto;
import me.braun.missionservice.api.dto.MissionDto;
import me.braun.missionservice.api.dto.SpacecraftDto;
import me.braun.missionservice.data.model.Mission;
import me.braun.missionservice.service.MissionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/missions")
public class MissionController {
    private final MissionService missionService;


    @GetMapping
    public ResponseEntity<List<Mission>> index(){
        return ResponseEntity.ok(missionService.getMissions());
    }

    @GetMapping(value = "/{id}/all")
    public ResponseEntity<Mission> showAll(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.getMissionById(id));
    }
    @GetMapping(value = "/{id}")
    public @NotNull ResponseEntity<MissionDto> show(@PathVariable long id) {
        MissionDto missionDto = missionService.missionToDto(missionService.getMissionById(id));
        return ResponseEntity.ok(missionDto);
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<AccountDto> getCustomerByMissionId(@PathVariable(name = "id") long id,
                                                             @RequestHeader HttpHeaders headers) {
        try {
            final AccountDto userDto = missionService.getCustomer(id, new HttpEntity<>(headers));

            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/spacecraft")
    public ResponseEntity<SpacecraftDto> getSpacecraftByMissionId(@PathVariable(name = "id") long id,
                                                                @RequestHeader HttpHeaders headers) {
        try {
            final SpacecraftDto craftDto = missionService.getSpacecraft(id, new HttpEntity<>(headers));

            return ResponseEntity.ok(craftDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody MissionDto missionDto){
        final String name = missionDto.getName();
        final String description = missionDto.getDescription();
        final long customerId = missionDto.getCustomerId();
        final long spacecraftId = missionDto.getSpaceCraftId();
        final Short statusId = missionDto.getStatusId();
        final long curatorId = missionDto.getCuratorId();
        final int payloadWeigh = missionDto.getPayloadWeight();
        final Date date = missionDto.getDate();
        final int duration = missionDto.getDuration();
        final byte serviceTypeId = missionDto.getServiceId();
        final long id = missionService.createMission(name, description, customerId, spacecraftId, statusId, curatorId,
                payloadWeigh, date, duration, serviceTypeId);
        final String location = String.format("/missions/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> change(@PathVariable Long id, @Valid @RequestBody MissionDto missionDto) {
        final String name = missionDto.getName();
        final String description = missionDto.getDescription();
        final Long customerId = missionDto.getCustomerId();
        final Long spacecraftId = missionDto.getSpaceCraftId();
        final Short statusId = missionDto.getStatusId();
        final Long curatorId = missionDto.getCuratorId();
        final Integer payloadWeigh = missionDto.getPayloadWeight();
        final Date date = missionDto.getDate();
        final Integer duration = missionDto.getDuration();
        final Byte serviceTypeId = missionDto.getServiceId();
        try {
            missionService.updateMission(id, name, description, customerId, spacecraftId, statusId, curatorId,
                    payloadWeigh, date, duration, serviceTypeId);
            final String location = String.format("/missions/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        missionService.deleteMission(id);

        return ResponseEntity.noContent().build();
    }

}
