package com.acme.bank.loan.entitlement.domain.event;

import java.time.ZonedDateTime;

public class PendingLoanEvent extends EnrichLoanEvent {

    private ZonedDateTime pendingTimestamp;

    public ZonedDateTime getPendingTimestamp() {
        return pendingTimestamp;
    }

    public void setPendingTimestamp(ZonedDateTime pendingTimestamp) {
        this.pendingTimestamp = pendingTimestamp;
    }
}
