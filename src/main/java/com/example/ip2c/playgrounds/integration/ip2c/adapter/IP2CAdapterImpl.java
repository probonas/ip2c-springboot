package com.example.ip2c.playgrounds.integration.ip2c.adapter;

import com.example.ip2c.playgrounds.integration.ip2c.model.IP2CException;
import com.example.ip2c.playgrounds.integration.ip2c.model.IP2CServiceResponse;
import com.hazelcast.com.google.common.base.CharMatcher;
import com.hazelcast.com.google.common.base.Splitter;
import com.hazelcast.com.google.common.collect.Iterables;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.TEXT_PLAIN;


@Component
public class IP2CAdapterImpl implements IP2CAdapter {
    private final String BASE_URL = "https://ip2c.org";

    private final char RESPONSE_TEXT_DELIMITER = ';';

    private final Splitter splitter = Splitter.on(CharMatcher.is(RESPONSE_TEXT_DELIMITER));

    private final RestClient restClient;

    public IP2CAdapterImpl(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl(BASE_URL).build();
    }

    @Override
    public IP2CServiceResponse get(String ip) {
        return restClient
                .get()
                .uri("/{ip}", ip)
                .accept(TEXT_PLAIN)
                .exchange((request, response) -> {
                    String[] responseParts = Iterables.toArray(splitter.split(response.bodyTo(String.class)), String.class);
                    if (responseParts.length != 4) {
                        throw new IP2CException("Received malformed response from external service!");
                    }
                    return new IP2CServiceResponse(
                            IP2CServiceResponse.ServiceResponseCode.fromValue(Integer.parseInt(responseParts[0])),
                            responseParts[1],
                            responseParts[2],
                            responseParts[3],
                            ip
                    );
                });
    }
}
