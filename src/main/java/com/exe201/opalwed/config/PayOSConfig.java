package com.exe201.opalwed.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
public class PayOSConfig {

    @Value("${payment.payos.client_id}")
    private String clientId;

    @Value("${payment.payos.api_key}")
    private String apiKey;

    @Value("${payment.payos.checksum_key}")
    private String checksumKey;

    @Bean
    public PayOS payOS() {
        return new PayOS(clientId, apiKey, checksumKey);
    }

}
