package com.example.ip2c.playgrounds.api;

import com.example.ip2c.playgrounds.api.model.IpInformationResponse;
import com.example.ip2c.playgrounds.service.IpAddressInformationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ApiClient {

    private final IpAddressInformationService ipAddressInformationService;

    @GetMapping(value = "info/{ip}", produces = "application/json")
    public ResponseEntity<IpInformationResponse> info(@Valid @PathVariable(required = true) String ip) {
        Optional<IpInformationResponse> ipAddressInformationResponse = ipAddressInformationService.fetch(ip);
        if (ipAddressInformationResponse.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.of(ipAddressInformationResponse);
    }

}
