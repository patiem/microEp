package com.epa.micro.resource.service.repository;

import com.epa.micro.resource.service.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResourceRepository extends JpaRepository<Resource, Long> {
}