package com.acme.bank.loan.domain.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.Properties;

@SuppressWarnings({"unused"})
@ConfigurationProperties(prefix = "acme")
@Component
public class AcmeProperties {

    private String applicationName;
    private final KafkaProperties kafkaProperties;

    public AcmeProperties(@Value("${spring.application.name}") String applicationName,
                          final KafkaProperties kafkaProperties) {
        this.applicationName = applicationName;
        this.kafkaProperties = kafkaProperties;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public Properties kafkaProperties(KafkaTopic kafkaTopic) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationName.concat("-").concat(kafkaTopic.getTopicName()));
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", kafkaProperties.getBootstrapServers()));
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Serdes.StringSerde.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerde.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Serdes.StringSerde.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerde.class);
        return config;
    }
}
