package com.acme.bank.loan.domain.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@ComponentScan({
        "com.acme.bank.loan.domain"
})
@Configurable
public class DomainAutoConfig {

    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }
}
