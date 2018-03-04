package com.acme.bank.loan.domain.converter;

import com.acme.bank.loan.domain.event.EnrichLoanEvent;
import com.acme.bank.loan.domain.event.PendingLoanEvent;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class EnrichLoanEventToPendingLoanEventConverter extends AbstractConverter<EnrichLoanEvent, PendingLoanEvent> {

    @Override
    public PendingLoanEvent convert(EnrichLoanEvent enrichLoanEvent) {
        PendingLoanEvent pendingLoanEvent = new PendingLoanEvent();
        pendingLoanEvent.setEventId(enrichLoanEvent.getEventId());
        pendingLoanEvent.setPersonId(enrichLoanEvent.getPersonId());
        pendingLoanEvent.setSsn(enrichLoanEvent.getSsn());
        pendingLoanEvent.setBirthDate(enrichLoanEvent.getBirthDate());
        pendingLoanEvent.setGender(enrichLoanEvent.getGender());
        pendingLoanEvent.setFirstName(enrichLoanEvent.getFirstName());
        pendingLoanEvent.setLastName(enrichLoanEvent.getLastName());
        pendingLoanEvent.setNationality(enrichLoanEvent.getNationality());
        pendingLoanEvent.setEnrichedTimestamp(enrichLoanEvent.getEnrichedTimestamp());
        pendingLoanEvent.setPendingTimestamp(ZonedDateTime.now());
        return pendingLoanEvent;
    }
}
