package com.acme.bank.loan.entitlement.service.rule;

import com.acme.bank.loan.entitlement.domain.event.EnrichLoanEvent;

public interface Rule {

    Outcome evaluate(EnrichLoanEvent event);
}
