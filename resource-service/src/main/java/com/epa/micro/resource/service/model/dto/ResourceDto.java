package com.epa.micro.resource.service.model.dto;

import com.epa.micro.resource.service.model.Resource;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ResourceDto {

    private Long id;
    private String fileName;
    private byte[] data;

    public Resource toResource() {
        return Resource.builder()
                .data(data)
                .fileName(fileName)
                .id(id)
                .build();
    }
}
