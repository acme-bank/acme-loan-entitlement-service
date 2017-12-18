package com.acme.bank.loan.entitlement.service.service;

import com.acme.bank.loan.entitlement.domain.event.EnrichLoanEvent;
import com.acme.bank.loan.entitlement.service.rule.Outcome;
import com.acme.bank.loan.entitlement.service.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleService.class);
    private final List<Rule> rules;

    @Autowired
    public RuleService(List<Rule> rules) {
        this.rules = rules;
    }

    public Outcome evaluate(final EnrichLoanEvent event) {
        if (matches(Outcome.REJECTED, event)) {
            return Outcome.REJECTED;
        } else if (matches(Outcome.PENDING, event)) {
            return Outcome.PENDING;
        } else {
            return Outcome.ENTITLED;
        }
    }

    private boolean matches(Outcome outcome, final EnrichLoanEvent event) {
        return rules.stream()
                .map(rule -> evaluate(rule, event))
                .anyMatch(outcome::equals);
    }

    private Outcome evaluate(final Rule rule, final EnrichLoanEvent event) {
        Outcome outcome = rule.evaluate(event);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Rule '{}' evaluated to '{}'", rule.getClass().getSimpleName(), outcome.name());
        }
        return outcome;
    }
}
