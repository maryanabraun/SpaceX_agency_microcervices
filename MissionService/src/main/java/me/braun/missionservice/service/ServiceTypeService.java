package me.braun.missionservice.service;


import lombok.extern.slf4j.Slf4j;
import me.braun.missionservice.data.model.ServiceType;
import me.braun.missionservice.data.repository.ServiceTypeRepository;
import me.braun.missionservice.exception.ServiceNotFoundExeption;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ServiceTypeService {
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public List<ServiceType> getServiceTypes(){return serviceTypeRepository.findAll();}

    public ServiceType getServiceTypeById(byte id) {
        Optional<ServiceType> serviceType = serviceTypeRepository.findById(id);
        if (serviceType.isPresent()) {
            log.info("service type: {}", serviceType.get());
            return serviceType.get();
        }
        throw new ServiceNotFoundExeption();}

    public Byte createService(String service){
        ServiceType serviceType = ServiceType.builder()
                .service(service).build();
        ServiceType saveService = serviceTypeRepository.save(serviceType);
        return saveService.getId();
    }

    public Byte updateService(byte id, String service) throws ServiceNotFoundExeption {
        final Optional<ServiceType> maybeServiceType = serviceTypeRepository.findById(id);
        if(maybeServiceType.isEmpty())
            throw new ServiceNotFoundExeption();
        final ServiceType serviceType = maybeServiceType.get();
        if (service != null && !service.isBlank()) serviceType.setService(service);
        ServiceType saveServiceType = serviceTypeRepository.save(serviceType);
        return saveServiceType.getId();
    }

    public void deleteService(byte id) {
        serviceTypeRepository.deleteById(id);
    }
}
