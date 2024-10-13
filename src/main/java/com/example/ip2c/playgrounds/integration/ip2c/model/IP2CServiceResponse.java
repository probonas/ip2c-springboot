package com.example.ip2c.playgrounds.integration.ip2c.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IP2CServiceResponse {
    private ServiceResponseCode serviceResponseCode;

    private String twoLetterIsoCode;
    private String threeLetterIsoCode;
    private String fullCountryName;
    private String ip;

    public enum ServiceResponseCode {
        WRONG_INPUT(0),
        SUCCESS(1),
        UNKNOWN_ERROR(2);

        private final int value;

        ServiceResponseCode(int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }

        public static ServiceResponseCode fromValue(int value) {
            return values()[value];
        }
    }
}
