package com.acme.bank.loan.entitlement.domain.converter;

import com.acme.bank.loan.entitlement.domain.event.EnrichLoanEvent;
import com.acme.bank.loan.entitlement.domain.event.RejectLoanEvent;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class EnrichLoanEventToRejectLoanEventConverter extends AbstractConverter<EnrichLoanEvent, RejectLoanEvent> {

    @Override
    public RejectLoanEvent convert(EnrichLoanEvent enrichLoanEvent) {
        RejectLoanEvent rejectLoanEvent = new RejectLoanEvent();
        rejectLoanEvent.setUuid(enrichLoanEvent.getUuid());
        rejectLoanEvent.setRejectedTimestamp(ZonedDateTime.now());
        return rejectLoanEvent;
    }
}
