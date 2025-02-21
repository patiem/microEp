package com.epa.micro.resource.service.service;

import com.epa.micro.resource.service.model.Resource;
import com.epa.micro.resource.service.model.dto.ResourceDto;
import com.epa.micro.resource.service.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public Long save(MultipartFile file) throws IOException {
        var resource = resourceRepository.save(
                Resource.builder()
                        .fileName(file.getOriginalFilename())
                        .data(file.getBytes())
                        .build());
        return resource.getId();
    }

    public ResourceDto getResourceById(Long id) {
        Resource resource = resourceRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Resource with ID=%d not found", id)));
        return resource.toResourceDto();
    }

    public List<Long> deleteResources(String idsToDelete) {
        List<Long> existingIds = Arrays.stream(idsToDelete.split(","))
                .map(Long::valueOf)
                .filter(id -> resourceRepository.existsById(id))
                .toList();
        resourceRepository.deleteAllById(existingIds);
        return existingIds;
    }
}
