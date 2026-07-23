package com.efranca.orderservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Mapeia o prefixo "services.*" do application.yml.
 * Em k8s, isso sera sobrescrito via env var SERVICES_PAYMENT_URL /
 * SERVICES_SHIPPING_URL apontando para os Service DNS names
 * (ex: http://payment-service:8088), o mesmo padrao de externalizacao
 * de config que voce ja usou no url-shortener com APP_ENVIRONMENT.
 */
@ConfigurationProperties(prefix = "services")
public class ServiceUrlsProperties {

    private String paymentUrl;
    private String shippingUrl;

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getShippingUrl() {
        return shippingUrl;
    }

    public void setShippingUrl(String shippingUrl) {
        this.shippingUrl = shippingUrl;
    }
}
