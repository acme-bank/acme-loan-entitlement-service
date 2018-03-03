package com.acme.bank.loan.service.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({
        "com.acme.bank.loan.service"
})
@Configurable
public class ServiceAutoConfig {
}
