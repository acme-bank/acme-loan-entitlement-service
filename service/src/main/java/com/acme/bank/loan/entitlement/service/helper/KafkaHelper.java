package com.acme.bank.loan.entitlement.service.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

@SuppressWarnings({"unused", "WeakerAccess", "Duplicates"})
@Component
public class KafkaHelper {

    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;

    public KafkaHelper(final KafkaProperties kafkaProperties,
                       @Qualifier("kafkaObjectMapper") final ObjectMapper objectMapper) {
        this.kafkaProperties = kafkaProperties;
        this.objectMapper = objectMapper;
    }

    public <T> Serde<T> jsonSerde(Class<T> clazz) {
        final Serializer<T> serializer = new JsonSerializer<>(objectMapper);
        final Deserializer<T> deserializer = new JsonDeserializer<>(clazz, objectMapper);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public Properties properties(String applicationId) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", kafkaProperties.getBootstrapServers()));
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset());
        return config;
    }

    public <T> Consumed<String, T> cosumedWith(Class<T> clazz) {
        return Consumed.with(Serdes.String(), jsonSerde(clazz));
    }

    public <T> Produced<String, T> producedWith(Class<T> clazz) {
        return Produced.with(Serdes.String(), jsonSerde(clazz));
    }
}
