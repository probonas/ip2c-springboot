package com.example.ip2c.playgrounds.events;

import com.example.ip2c.playgrounds.dao.jpa.entities.CountryEntity;
import com.example.ip2c.playgrounds.dao.jpa.entities.IpAddressEntity;
import com.example.ip2c.playgrounds.dao.jpa.repos.CountriesRepository;
import com.example.ip2c.playgrounds.dao.jpa.repos.IpAddressesRepository;
import com.example.ip2c.playgrounds.integration.ip2c.model.IP2CServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class RegisterNewIpEventListener implements ApplicationListener<RegisterNewIpEvent> {
    private final CountriesRepository countriesRepository;
    private final IpAddressesRepository ipAddressesRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void onApplicationEvent(RegisterNewIpEvent event) {
        IP2CServiceResponse ip2CServiceResponse = (IP2CServiceResponse) event.getSource();

        CountryEntity countryEntity = countriesRepository.findCountryByTwoLetterCode(ip2CServiceResponse.getTwoLetterIsoCode());
        if (countryEntity == null) {
            CountryEntity newCountryEntity = new CountryEntity();
            newCountryEntity.setName(ip2CServiceResponse.getFullCountryName());
            newCountryEntity.setTwoLetterCode(ip2CServiceResponse.getTwoLetterIsoCode());
            newCountryEntity.setThreeLetterCode(ip2CServiceResponse.getThreeLetterIsoCode());
            newCountryEntity.setCreatedAt(ZonedDateTime.ofInstant(Instant.ofEpochMilli(event.getTimestamp()), ZoneId.of("UTC")));

            countriesRepository.save(newCountryEntity);
            countryEntity = newCountryEntity;
        }

        IpAddressEntity ipAddressEntity = ipAddressesRepository.findByIp(ip2CServiceResponse.getIp());
        if (ipAddressEntity == null) {
            ipAddressEntity = new IpAddressEntity();
            ipAddressEntity.setCreatedAt(ZonedDateTime.ofInstant(Instant.ofEpochMilli(event.getTimestamp()), ZoneId.of("UTC")));
            ipAddressEntity.setUpdatedAt(ipAddressEntity.getUpdatedAt());
        } else {
            ipAddressEntity.setUpdatedAt(ZonedDateTime.ofInstant(Instant.ofEpochMilli(event.getTimestamp()), ZoneId.of("UTC")));
        }

        ipAddressEntity.setIp(ip2CServiceResponse.getIp());
        ipAddressEntity.setCountryEntity(countryEntity);
        ipAddressesRepository.save(ipAddressEntity);
    }
}
