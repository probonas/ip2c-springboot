package com.example.ip2c.playgrounds.service;

import com.example.ip2c.playgrounds.api.model.IpInformationResponse;

import java.util.Optional;

public interface IpAddressInformationService {

    Optional<IpInformationResponse> fetch(String ip);

    boolean refresh(String ip);
}
