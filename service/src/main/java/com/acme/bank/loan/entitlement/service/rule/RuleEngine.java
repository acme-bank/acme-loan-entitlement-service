package com.acme.bank.loan.entitlement.service.rule;

import com.acme.bank.loan.entitlement.domain.event.EnrichLoanEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngine.class);
    private final List<Rule> rules;

    @Autowired
    public RuleEngine(List<Rule> rules) {
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
