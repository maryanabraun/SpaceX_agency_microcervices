package me.braun.missionservice.api.controller;

import me.braun.missionservice.api.dto.StatusDto;
import me.braun.missionservice.data.model.Status;
import me.braun.missionservice.service.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequestMapping(value = "/statuses")
@RestController
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }
    @GetMapping
    public ResponseEntity<List<Status>> index(){return ResponseEntity.ok(statusService.getStatuses());}

    @GetMapping(value = "/{id}")
    public ResponseEntity<Status> show(@PathVariable short id){
        return ResponseEntity.ok(statusService.getStatusById(id));
    }
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody StatusDto statusDto){
        final String status = statusDto.getStatus();

        final short id = statusService.createStatus(status);
        final String location = String.format("/statuses/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> change(@PathVariable short id, @Valid @RequestBody StatusDto statusDto) {
        final String status = statusDto.getStatus();
        try {
            statusService.updateStatus(id, status);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable short id) {
        statusService.deleteAccount(id);

        return ResponseEntity.noContent().build();
    }




}
