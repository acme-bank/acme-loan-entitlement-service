package com.acme.bank.loan.domain.config;

public enum KafkaTopic {

    ENRICHED_LOANS("enriched-loans"),
    ENTITLED_LOANS("entitled-loans"),
    PENDING_LOANS("pending-loans"),
    REJECTED_LOANS("rejected-loans");

    private String topicName;

    KafkaTopic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
