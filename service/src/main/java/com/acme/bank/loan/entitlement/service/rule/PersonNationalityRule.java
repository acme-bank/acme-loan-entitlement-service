package com.acme.bank.loan.entitlement.service.rule;

import com.acme.bank.loan.entitlement.domain.event.EnrichLoanEvent;
import com.acme.bank.loan.entitlement.domain.model.Country;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonNationalityRule implements Rule {

    @Override
    public Outcome evaluate(final EnrichLoanEvent event) {
        return isEntitled(event) ? Outcome.ENTITLED : Outcome.PENDING;
    }

    private boolean isEntitled(final EnrichLoanEvent event) {
        return Optional.ofNullable(event)
                .map(EnrichLoanEvent::getNationality)
                .filter(Country.NO::equals)
                .isPresent();
    }
}
