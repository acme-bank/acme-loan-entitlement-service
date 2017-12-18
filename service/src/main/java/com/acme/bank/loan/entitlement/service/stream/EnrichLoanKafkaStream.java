package com.acme.bank.loan.entitlement.service.stream;

import com.acme.bank.loan.entitlement.domain.config.AcmeProperties;
import com.acme.bank.loan.entitlement.domain.event.EnrichLoanEvent;
import com.acme.bank.loan.entitlement.domain.event.EntitleLoanEvent;
import com.acme.bank.loan.entitlement.domain.event.PendingLoanEvent;
import com.acme.bank.loan.entitlement.domain.event.RejectLoanEvent;
import com.acme.bank.loan.entitlement.service.helper.KafkaHelper;
import com.acme.bank.loan.entitlement.service.rule.Outcome;
import com.acme.bank.loan.entitlement.service.service.RuleService;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;

@SuppressWarnings({"Duplicates", "unchecked", "unused"})
@Component
public class EnrichLoanKafkaStream {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichLoanKafkaStream.class);

    private String applicationName;
    private final AcmeProperties acmeProperties;
    private KafkaStreams streams;
    private final KafkaHelper kafkaHelper;
    private final ConversionService conversionService;
    private final RuleService ruleService;

    @Autowired
    public EnrichLoanKafkaStream(@Value("${spring.application.name}") String applicationName,
                                 final AcmeProperties acmeProperties,
                                 final KafkaHelper kafkaHelper,
                                 final ConversionService conversionService,
                                 final RuleService ruleService) {
        this.applicationName = applicationName;
        this.acmeProperties = acmeProperties;
        this.kafkaHelper = kafkaHelper;
        this.conversionService = conversionService;
        this.ruleService = ruleService;
    }

    @PostConstruct
    public void startStream() {
        AcmeProperties.Kafka.Topics topics = acmeProperties.getKafka().getTopics();

        StreamsBuilder streamBuilder = new StreamsBuilder();
        KStream<String, EnrichLoanEvent>[] streamBranches = streamBuilder
                .stream(topics.getEnrichLoan(), kafkaHelper.consumedWith(EnrichLoanEvent.class))
                .branch(this::entitleLoan, this::pendingLoan, this::rejectLoan);

        send(streamBranches[0], EntitleLoanEvent.class, topics.getEntitleLoan());
        send(streamBranches[1], PendingLoanEvent.class, topics.getPendingLoan());
        send(streamBranches[2], RejectLoanEvent.class, topics.getRejectLoan());

        streams = new KafkaStreams(streamBuilder.build(), properties());
        streams.start();
    }

    private boolean entitleLoan(String key, EnrichLoanEvent event) {
        AcmeProperties.Kafka.Topics topics = acmeProperties.getKafka().getTopics();

        LOGGER.info("Received event with key {} on topic {}", event.getUuid(), topics.getEnrichLoan());

        return Outcome.ENTITLED.equals(ruleService.evaluate(event));
    }

    private boolean pendingLoan(String key, EnrichLoanEvent event) { // NOSONAR
        return Outcome.PENDING.equals(ruleService.evaluate(event));
    }

    private boolean rejectLoan(String key, EnrichLoanEvent event) { // NOSONAR
        return Boolean.TRUE; // Always reject if validation fails
    }

    private <T> void send(KStream<String, EnrichLoanEvent> stream, Class<T> clazz, String targetTopic) {
        stream.map((key, value) -> convert(key, value, clazz, targetTopic))
                .to(targetTopic, kafkaHelper.producedWith(clazz));
    }

    private <T> KeyValue<String, T> convert(String key, EnrichLoanEvent event, Class<T> clazz, String targetTopic) {
        LOGGER.info("Sending event with key {} to topic {}", key, targetTopic);

        return new KeyValue<>(key, conversionService.convert(event, clazz));
    }

    private Properties properties() {
        AcmeProperties.Kafka.Topics topics = acmeProperties.getKafka().getTopics();
        return kafkaHelper.properties(applicationName.concat("-").concat(topics.getEnrichLoan()));
    }

    @PreDestroy
    public void closeStream() {
        if (streams != null) {
            streams.close();
        }
    }
}
