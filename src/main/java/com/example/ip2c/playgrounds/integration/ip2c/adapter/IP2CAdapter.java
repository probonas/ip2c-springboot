package com.example.ip2c.playgrounds.integration.ip2c.adapter;

import com.example.ip2c.playgrounds.integration.ip2c.model.IP2CServiceResponse;

public interface IP2CAdapter {
    IP2CServiceResponse get(String ip);
}
