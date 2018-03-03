package com.acme.bank.loan.service.service;

import com.acme.bank.loan.domain.event.EnrichLoanEvent;
import com.acme.bank.loan.service.rule.Outcome;
import com.acme.bank.loan.service.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleService.class);
    private final List<Rule> rules;

    @Autowired
    public RuleService(List<Rule> rules) {
        this.rules = rules;
    }

    public Outcome evaluate(final EnrichLoanEvent event) {
        List<Outcome> outcomes = evaluateRules(event);

        if (outcomes.stream().anyMatch(Outcome.REJECTED::equals)) {
            return Outcome.REJECTED;
        } else if (outcomes.stream().anyMatch(Outcome.PENDING::equals)) {
            return Outcome.PENDING;
        } else {
            return Outcome.ENTITLED;
        }
    }

    private List<Outcome> evaluateRules(final EnrichLoanEvent event) {
        return rules.stream()
                .map(rule -> evaluateRule(rule, event))
                .collect(Collectors.toList());
    }

    private Outcome evaluateRule(final Rule rule, final EnrichLoanEvent event) {
        Outcome outcome = rule.evaluate(event);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Rule '{}' evaluated to '{}'", rule.getClass().getSimpleName(), outcome.name());
        }
        return outcome;
    }
}
