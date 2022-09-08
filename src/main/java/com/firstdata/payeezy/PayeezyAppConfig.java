package com.firstdata.payeezy;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class PayeezyAppConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder().defaultMessageConverters().build();
    }
}
