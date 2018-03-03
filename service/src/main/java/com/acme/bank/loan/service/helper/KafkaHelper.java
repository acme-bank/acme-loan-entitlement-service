package com.acme.bank.loan.service.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@SuppressWarnings({"unused", "WeakerAccess", "Duplicates"})
@Component
public class KafkaHelper {

    private final ObjectMapper objectMapper;

    public KafkaHelper(@Qualifier("kafkaObjectMapper") final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> Consumed<String, T> consumedWith(Class<T> targetType) {
        return Consumed.with(Serdes.String(), jsonSerde(targetType));
    }

    public <T> Produced<String, T> producedWith(Class<T> targetType) {
        return Produced.with(Serdes.String(), jsonSerde(targetType));
    }

    public <T> Serde<T> jsonSerde(Class<T> targetType) {
        return new JsonSerde<>(targetType, objectMapper);
    }

}
