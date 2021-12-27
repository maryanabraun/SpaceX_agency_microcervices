package me.braun.missionservice.api.controller;

import me.braun.missionservice.api.dto.ServiceTypeDto;
import me.braun.missionservice.data.model.ServiceType;
import me.braun.missionservice.service.ServiceTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/services")
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService;

    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }
    @GetMapping
    public ResponseEntity<List<ServiceType>> getServices(){
        return ResponseEntity.ok(serviceTypeService.getServiceTypes());}
    @GetMapping(value = "/{id}")
    public ResponseEntity<ServiceType> show(@PathVariable byte id) {
        return ResponseEntity.ok(serviceTypeService.getServiceTypeById(id));
    }
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ServiceTypeDto serviceTypeDto){
        final String service = serviceTypeDto.getServiceType();

        final byte id = serviceTypeService.createService(service);
        final String location = String.format("/services/%d", id);
        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> change(@PathVariable byte id, @Valid @RequestBody ServiceTypeDto serviceTypeDto) {
        final String service = serviceTypeDto.getServiceType();
        try {
            serviceTypeService.updateService(id, service);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable byte id) {
       serviceTypeService.deleteService(id);

        return ResponseEntity.noContent().build();
    }
}
