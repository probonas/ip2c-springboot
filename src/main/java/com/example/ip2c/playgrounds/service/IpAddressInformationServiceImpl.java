package com.example.ip2c.playgrounds.service;

import com.example.ip2c.playgrounds.api.model.IpInformationResponse;
import com.example.ip2c.playgrounds.dao.jpa.entities.IpAddressEntity;
import com.example.ip2c.playgrounds.dao.jpa.repos.IpAddressesRepository;
import com.example.ip2c.playgrounds.events.RegisterNewIpEvent;
import com.example.ip2c.playgrounds.integration.ip2c.adapter.IP2CAdapter;
import com.example.ip2c.playgrounds.integration.ip2c.model.IP2CException;
import com.example.ip2c.playgrounds.integration.ip2c.model.IP2CServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.ip2c.playgrounds.integration.ip2c.model.IP2CServiceResponse.ServiceResponseCode;

@Service
@RequiredArgsConstructor
@Slf4j
public class IpAddressInformationServiceImpl implements IpAddressInformationService {

    private final IP2CAdapter ip2CAdapter;
    private final IpAddressesRepository ipAddressesRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<IpInformationResponse> fetch(String ip) throws IP2CException {
        IpAddressEntity ipAddressEntity = ipAddressesRepository.findByIp(ip);

        if (ipAddressEntity != null) {
            log.info("IP info for {} was found in db!", ip);
            return Optional.of(new IpInformationResponse(ipAddressEntity));
        }

        return fetchFromExternalService(ip);
    }

    @Override
    public boolean refresh(String ip) {
        return fetchFromExternalService(ip).isPresent();
    }

    private Optional<IpInformationResponse> fetchFromExternalService(String ip) {
        IP2CServiceResponse ip2CServiceResponse = ip2CAdapter.get(ip);

        if (ip2CServiceResponse.getServiceResponseCode() == ServiceResponseCode.WRONG_INPUT || ip2CServiceResponse.getServiceResponseCode() == ServiceResponseCode.UNKNOWN_ERROR) {
            log.error("Service responded with {} for IP {}. No action performed...", ip2CServiceResponse.getServiceResponseCode().name(), ip);
            return Optional.empty();
        } else {
            applicationEventPublisher.publishEvent(new RegisterNewIpEvent(ip2CServiceResponse));
        }

        return Optional.of(new IpInformationResponse(ip2CServiceResponse));
    }
}
