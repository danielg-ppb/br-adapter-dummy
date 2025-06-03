package com.br.adapter.dummy.adapterdummy;

import flutter.gstt.data.generic_external_entity.GenericExternalEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Schema;
import org.springframework.pulsar.PulsarException;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static org.apache.pulsar.client.api.BatcherBuilder.KEY_BASED;

@Slf4j
@Component
public class AdapterPublisher {
    private final PulsarTemplate<GenericExternalEntity.EntityEnvelope> producer;


    public AdapterPublisher(PulsarTemplate<GenericExternalEntity.EntityEnvelope> producer) {
        this.producer = producer;
    }

    public MessageId sendAdapterOutboundMessage(
            String messageKey,
            GenericExternalEntity.EntityEnvelope entityEnvelope) {
        try {
            return producer.newMessage(entityEnvelope)
                    .withProducerCustomizer(producerBuilder -> producerBuilder.batcherBuilder(KEY_BASED))
                    .withMessageCustomizer(messageBuilder -> messageBuilder.key(messageKey))
                    .withSchema(Schema.PROTOBUF(GenericExternalEntity.EntityEnvelope.class))
                    .send();
        } catch (Exception e) {
            log.error("Message failed to be sent to Pulsar topic. Message ID: {}. Error: {}",
                    messageKey, e.getMessage(), e);
            throw new PulsarException("Failed to send message to pulsar. Message ID: " + messageKey, e);
        }
    }
}
