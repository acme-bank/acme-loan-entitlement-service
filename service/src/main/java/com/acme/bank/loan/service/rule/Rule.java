package com.acme.bank.loan.service.rule;

import com.acme.bank.loan.domain.event.EnrichLoanEvent;

public interface Rule {

    Outcome evaluate(EnrichLoanEvent event);
}
