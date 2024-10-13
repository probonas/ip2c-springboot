package com.example.ip2c.playgrounds.service;

import com.example.ip2c.playgrounds.api.model.IpInformationResponse;
import com.example.ip2c.playgrounds.cache.CacheService;
import com.example.ip2c.playgrounds.dao.jpa.repos.IpAddressesRepository;
import com.example.ip2c.playgrounds.integration.ip2c.adapter.IP2CAdapter;
import com.example.ip2c.playgrounds.integration.ip2c.model.IP2CException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
@Slf4j
@ConditionalOnProperty(name = "use.cache", havingValue = "true")
public class IpAddressInformationCachedServiceImpl extends IpAddressInformationServiceImpl {

    private final CacheService cacheService;


    public IpAddressInformationCachedServiceImpl(CacheService cacheService, IP2CAdapter ip2CAdapter, IpAddressesRepository ipAddressesRepository, ApplicationEventPublisher applicationEventPublisher) {
        super(ip2CAdapter, ipAddressesRepository, applicationEventPublisher);
        this.cacheService = cacheService;
    }

    @Override
    public Optional<IpInformationResponse> fetch(String ip) throws IP2CException {

        if (cacheService.contains(ip)) {
            log.info("IP info for {} was found in cache!", ip);
            return Optional.of(cacheService.get(ip));
        }

        Optional<IpInformationResponse> ipAddressInformationResponse = super.fetch(ip);

        if (ipAddressInformationResponse.isPresent()) {
            log.info("IP info for {} was added to cache!", ip);
            cacheService.put(ip, ipAddressInformationResponse.get());
        }

        return ipAddressInformationResponse;
    }

    @Override
    public boolean refresh(String ip) {
        cacheService.invalidate(ip);
        return super.refresh(ip);
    }
}
