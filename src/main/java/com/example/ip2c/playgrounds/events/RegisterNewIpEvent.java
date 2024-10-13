package com.example.ip2c.playgrounds.events;

import com.example.ip2c.playgrounds.integration.ip2c.model.IP2CServiceResponse;
import org.springframework.context.ApplicationEvent;

public class RegisterNewIpEvent extends ApplicationEvent {
    private static final long serialVersionUID = 55L;

    public RegisterNewIpEvent(IP2CServiceResponse serviceResponse) {
        super(serviceResponse);
    }
}
