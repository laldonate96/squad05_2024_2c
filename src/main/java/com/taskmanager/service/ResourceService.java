package com.taskmanager.service;

import com.taskmanager.dto.ResourceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class ResourceService {
    @Autowired
    private RestTemplate restTemplate;

    private final String RESOURCES_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/recursos-api/1.0.0/m/recursos";

    public List<ResourceDTO> getAllResources() {
        return Arrays.asList(restTemplate.getForObject(RESOURCES_URL, ResourceDTO[].class));
    }

    public ResourceDTO getResourceById(String resourceId) {
        return getAllResources().stream()
                .filter(resource -> resource.getId().equals(resourceId))
                .findFirst()
                .orElse(null);
    }
}