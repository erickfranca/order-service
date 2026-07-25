package com.efranca.orderservice.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * RestClient e a API sincrona recomendada pelo Spring (substitui o
 * RestTemplate, que esta em modo de manutencao). Cada bean aqui aponta
 * para um servico downstream diferente - e essas chamadas HTTP sincronas
 * sao exatamente o que faz o trace "atravessar" processos.
 *
 * IMPORTANTE: injetamos o RestClient.Builder (em vez de chamar o metodo
 * estatico RestClient.builder()). O builder injetado e auto-configurado
 * pelo Spring Boot com toda a infraestrutura de observabilidade - e essa
 * infraestrutura e o que insere o header "traceparent" automaticamente
 * na chamada de saida quando o tracing estiver habilitado. Se voce criar
 * o client a partir do builder estatico, essa propagacao nao acontece.
 */
@Configuration
@EnableConfigurationProperties(ServiceUrlsProperties.class)
public class RestClientConfig {

    @Bean
    public RestClient paymentRestClient(RestClient.Builder builder, ServiceUrlsProperties properties) {
        return builder
                .baseUrl(properties.getPaymentUrl())
                .build();
    }

    @Bean
    public RestClient shippingRestClient(RestClient.Builder builder, ServiceUrlsProperties properties) {
        return builder
                .baseUrl(properties.getShippingUrl())
                .build();
    }
}