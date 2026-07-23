package com.efranca.orderservice.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * RestClient e a API sincrona recomendada pelo Spring (substitui o
 * RestTemplate, que esta em modo de manutencao). Cada bean aqui aponta
 * para um servico downstream diferente - e essas chamadas HTTP sincronas
 * sao exatamente o que faz o trace "atravessar" processos: quando voce
 * habilitar tracing, o Spring instrumenta esse client automaticamente
 * para propagar o header de trace (traceparent) na requisicao de saida.
 */
@Configuration
@EnableConfigurationProperties(ServiceUrlsProperties.class)
public class RestClientConfig {

    @Bean
    public RestClient paymentRestClient(ServiceUrlsProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.getPaymentUrl())
                .build();
    }

    @Bean
    public RestClient shippingRestClient(ServiceUrlsProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.getShippingUrl())
                .build();
    }
}
