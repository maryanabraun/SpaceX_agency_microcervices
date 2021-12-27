package me.braun.identityservice.api.controller;


import me.braun.identityservice.data.model.Role;
import me.braun.identityservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/roles")
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> index(){
        return ResponseEntity.ok(roleService.getroles());}

    @GetMapping(value = "/{id}")
    public ResponseEntity<Role> show(@PathVariable long id) {
        return ResponseEntity.ok(roleService.getroleById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

}
