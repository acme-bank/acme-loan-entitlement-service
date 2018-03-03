package com.acme.bank.loan.service.stream;

import com.acme.bank.loan.domain.config.AcmeProperties;
import com.acme.bank.loan.domain.config.KafkaTopic;
import com.acme.bank.loan.domain.event.EnrichLoanEvent;
import com.acme.bank.loan.domain.event.EntitleLoanEvent;
import com.acme.bank.loan.domain.event.PendingLoanEvent;
import com.acme.bank.loan.domain.event.RejectLoanEvent;
import com.acme.bank.loan.service.helper.KafkaHelper;
import com.acme.bank.loan.service.rule.Outcome;
import com.acme.bank.loan.service.service.RuleService;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SuppressWarnings({"Duplicates", "unchecked", "unused"})
@Component
public class EnrichLoanKafkaStream {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichLoanKafkaStream.class);

    private final AcmeProperties acmeProperties;
    private KafkaStreams streams;
    private final KafkaHelper kafkaHelper;
    private final ConversionService conversionService;
    private final RuleService ruleService;

    @Autowired
    public EnrichLoanKafkaStream(final AcmeProperties acmeProperties,
                                 final KafkaHelper kafkaHelper,
                                 final ConversionService conversionService,
                                 final RuleService ruleService) {
        this.acmeProperties = acmeProperties;
        this.kafkaHelper = kafkaHelper;
        this.conversionService = conversionService;
        this.ruleService = ruleService;
    }

    @PostConstruct
    public void startStream() {
        StreamsBuilder streamBuilder = new StreamsBuilder();
        KStream<String, EnrichLoanEvent>[] streamBranches = streamBuilder
                .stream(KafkaTopic.ENRICHED_LOANS.getTopicName(), kafkaHelper.consumedWith(EnrichLoanEvent.class))
                .branch(this::entitleLoan, this::pendingLoan, this::rejectLoan);

        send(streamBranches[0], EntitleLoanEvent.class, KafkaTopic.ENTITLED_LOANS);
        send(streamBranches[1], PendingLoanEvent.class, KafkaTopic.PENDING_LOANS);
        send(streamBranches[2], RejectLoanEvent.class, KafkaTopic.REJECTED_LOANS);

        streams = new KafkaStreams(streamBuilder.build(), acmeProperties.kafkaProperties(KafkaTopic.ENRICHED_LOANS));
        streams.start();
    }

    private boolean entitleLoan(String key, EnrichLoanEvent event) {
        LOGGER.info("Received event with key {} on topic {}", event.getUuid(), KafkaTopic.ENRICHED_LOANS.getTopicName());

        return Outcome.ENTITLED.equals(ruleService.evaluate(event));
    }

    private boolean pendingLoan(String key, EnrichLoanEvent event) {
        return Outcome.PENDING.equals(ruleService.evaluate(event));
    }

    private boolean rejectLoan(String key, EnrichLoanEvent event) {
        return Boolean.TRUE; // Always reject if validation fails
    }

    private <T> void send(KStream<String, EnrichLoanEvent> stream, Class<T> clazz, KafkaTopic targetTopic) {
        stream.map((key, value) -> convert(key, value, clazz, targetTopic))
                .to(targetTopic.getTopicName(), kafkaHelper.producedWith(clazz));
    }

    private <T> KeyValue<String, T> convert(String key, EnrichLoanEvent event, Class<T> clazz, KafkaTopic targetTopic) {
        LOGGER.info("Sending event with key {} to topic {}", key, targetTopic.getTopicName());

        return new KeyValue<>(key, conversionService.convert(event, clazz));
    }

    @PreDestroy
    public void closeStream() {
        if (streams != null) {
            streams.close();
        }
    }
}
