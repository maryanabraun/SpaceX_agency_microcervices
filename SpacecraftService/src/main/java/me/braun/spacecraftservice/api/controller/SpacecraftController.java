package me.braun.spacecraftservice.api.controller;


import lombok.RequiredArgsConstructor;
import me.braun.spacecraftservice.api.dto.SpacecraftDto;
import me.braun.spacecraftservice.data.model.Spacecraft;
import me.braun.spacecraftservice.service.SpacecraftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/spacecrafts")
public class SpacecraftController {
    private final SpacecraftService spacecraftService;

    @GetMapping
    public ResponseEntity<List<Spacecraft>> index(){
        return ResponseEntity.ok(spacecraftService.getSpacecrafts());}

    @GetMapping(value = "/{id}")
    public ResponseEntity<Spacecraft> show(@PathVariable Long id) {
        return ResponseEntity.ok(spacecraftService.getSpacecraftById(id));
    }



    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody SpacecraftDto spacecraftDto){
        final byte nameId = spacecraftDto.getNameId();
        final double capacity = spacecraftDto.getCapacity();
        final int maxWeight = spacecraftDto.getMaxWeight();
        final int launchPrice = spacecraftDto.getLaunchPrice();
        final long id = spacecraftService.createSpacecraft(nameId, capacity, maxWeight, launchPrice);
        final String location = String.format("/spacecrafts/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @Valid @RequestBody SpacecraftDto spacecraftDto) {
        final byte nameId = spacecraftDto.getNameId();
        final double capacity = spacecraftDto.getCapacity();
        final int maxWeight = spacecraftDto.getMaxWeight();
        final int launchPrice = spacecraftDto.getLaunchPrice();
        try {
            spacecraftService.updateSpacecraft(id, nameId, capacity, maxWeight, launchPrice);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        spacecraftService.deleteSpacecraft(id);

        return ResponseEntity.noContent().build();
    }

}
