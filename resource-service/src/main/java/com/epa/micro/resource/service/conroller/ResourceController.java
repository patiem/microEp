package com.epa.micro.resource.service.conroller;

import com.epa.micro.resource.service.model.dto.ResourceDto;
import com.epa.micro.resource.service.service.ResourceService;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(consumes = "audio/mpeg", produces = "application/json")
    public ResponseEntity<Map<String, Long>> uploadResource(@RequestParam("file") MultipartFile file) throws IOException {
        var savedResourceId = resourceService.save(file);
        return ResponseEntity.ok(Map.of("id", savedResourceId));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ResourceDto> getResourceById(@PathVariable Long id) {
        var resourceData = resourceService.getResourceById(id);
        return ResponseEntity.ok(resourceData);
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<Map<String, List<Long>>> deleteSong(@RequestParam("id")
                                                                  @Size(min = 1, max = 200, message = "Wrong chars number for ids - must me [1,200]")
                                                                  @Pattern(regexp = "^[0-9,]+$", message = "Incorrect format ids - Only numbers separated with coma allowed") String ids) {
        var idList = resourceService.deleteResources(ids);
        return ResponseEntity.ok(Map.of("ids", idList));
    }
}