package com.acme.bank.loan.domain.converter;

import com.acme.bank.loan.domain.event.EnrichLoanEvent;
import com.acme.bank.loan.domain.event.EntitleLoanEvent;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class EnrichLoanEventToEntitleLoanEventConverter extends AbstractConverter<EnrichLoanEvent, EntitleLoanEvent> {

    @Override
    public EntitleLoanEvent convert(EnrichLoanEvent enrichLoanEvent) {
        EntitleLoanEvent entitleLoanEvent = new EntitleLoanEvent();
        entitleLoanEvent.setUuid(enrichLoanEvent.getUuid());
        entitleLoanEvent.setEntitledTimestamp(ZonedDateTime.now());
        return entitleLoanEvent;
    }
}
