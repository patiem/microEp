package com.epa.micro.resource.service.model;

import com.epa.micro.resource.service.model.dto.ResourceDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;


@Entity
@Data
@Builder
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data;

    private String fileName;

    public ResourceDto toResourceDto() {
        return ResourceDto.builder()
                .id(id)
                .fileName(fileName)
                .data(data)
                .build();
    }
}
