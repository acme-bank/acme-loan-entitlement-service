package com.acme.bank.loan.entitlement.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@SuppressWarnings({"unused"})
@ConfigurationProperties(prefix = "acme")
@Component
public class AcmeProperties {

    private Kafka kafka = new Kafka();

    public Kafka getKafka() {
        return kafka;
    }

    public void setKafka(Kafka kafka) {
        this.kafka = kafka;
    }

    public class Kafka {

        private Topics topics = new Topics();

        public Topics getTopics() {
            return topics;
        }

        public void setTopics(Topics topics) {
            this.topics = topics;
        }

        public class Topics {

            private String registerLoan;
            private String validateLoan;
            private String enrichLoan;
            private String pendingLoan;
            private String augmentLoan;
            private String entitleLoan;
            private String rejectLoan;

            public String getRegisterLoan() {
                return registerLoan;
            }

            public void setRegisterLoan(String registerLoan) {
                this.registerLoan = registerLoan;
            }

            public String getValidateLoan() {
                return validateLoan;
            }

            public void setValidateLoan(String validateLoan) {
                this.validateLoan = validateLoan;
            }

            public String getEnrichLoan() {
                return enrichLoan;
            }

            public void setEnrichLoan(String enrichLoan) {
                this.enrichLoan = enrichLoan;
            }

            public String getPendingLoan() {
                return pendingLoan;
            }

            public void setPendingLoan(String pendingLoan) {
                this.pendingLoan = pendingLoan;
            }

            public String getAugmentLoan() {
                return augmentLoan;
            }

            public void setAugmentLoan(String augmentLoan) {
                this.augmentLoan = augmentLoan;
            }

            public String getEntitleLoan() {
                return entitleLoan;
            }

            public void setEntitleLoan(String entitleLoan) {
                this.entitleLoan = entitleLoan;
            }

            public String getRejectLoan() {
                return rejectLoan;
            }

            public void setRejectLoan(String rejectLoan) {
                this.rejectLoan = rejectLoan;
            }
        }
    }
}
