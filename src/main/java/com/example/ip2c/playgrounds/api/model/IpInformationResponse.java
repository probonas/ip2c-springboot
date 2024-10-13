package com.example.ip2c.playgrounds.api.model;

import com.example.ip2c.playgrounds.dao.jpa.entities.IpAddressEntity;
import com.example.ip2c.playgrounds.integration.ip2c.model.IP2CServiceResponse;
import lombok.Data;

import java.io.Serializable;

@Data
public class IpInformationResponse implements Serializable {
    private static final long serialVersionUID = 66L;

    private String ipAddress;
    private String countryName;
    private String twoLetterCode;
    private String threeLetterCode;

    public IpInformationResponse(IpAddressEntity ipAddressEntity) {
        this.ipAddress = ipAddressEntity.getIp();
        this.countryName = ipAddressEntity.getCountryEntity().getName();
        this.twoLetterCode = ipAddressEntity.getCountryEntity().getTwoLetterCode();
        this.threeLetterCode = ipAddressEntity.getCountryEntity().getThreeLetterCode();
    }

    public IpInformationResponse(IP2CServiceResponse ip2CServiceResponse) {
        this.ipAddress = ip2CServiceResponse.getIp();
        this.countryName = ip2CServiceResponse.getFullCountryName();
        this.twoLetterCode = ip2CServiceResponse.getTwoLetterIsoCode();
        this.threeLetterCode = ip2CServiceResponse.getThreeLetterIsoCode();
    }


}
