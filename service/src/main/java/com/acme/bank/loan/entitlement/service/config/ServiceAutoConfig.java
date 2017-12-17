package com.acme.bank.loan.entitlement.service.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({
        "com.acme.bank.loan.entitlement.service"
})
@Configurable
public class ServiceAutoConfig {
}
