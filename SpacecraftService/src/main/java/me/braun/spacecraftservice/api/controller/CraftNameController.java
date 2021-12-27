package me.braun.spacecraftservice.api.controller;


import me.braun.spacecraftservice.data.model.CraftName;
import me.braun.spacecraftservice.service.CraftNameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/craft_titles" )
public class CraftNameController {
    private final CraftNameService craftNameService;

    public CraftNameController(CraftNameService craftNameService) {
        this.craftNameService = craftNameService;
    }
    @GetMapping
    public ResponseEntity<List<CraftName>> getCraftNames(){
        return ResponseEntity.ok(craftNameService.getCraftNames());}

    @GetMapping(value = "/{id}")
    public ResponseEntity<CraftName> show(@PathVariable byte id) {
        return ResponseEntity.ok(craftNameService.getCraftNameById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable byte id) {
        craftNameService.deleteCraftName(id);

        return ResponseEntity.noContent().build();
    }

}
