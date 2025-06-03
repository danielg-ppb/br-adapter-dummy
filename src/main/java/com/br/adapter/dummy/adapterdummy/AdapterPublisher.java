package com.br.adapter.dummy.adapterdummy;

import flutter.gstt.data.betradar_uof.CollectorEnvelopeUofProto;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Schema;
import org.springframework.pulsar.PulsarException;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Component;

import static org.apache.pulsar.client.api.BatcherBuilder.KEY_BASED;

@Component
public class AdapterPublisher {
    private final PulsarTemplate<CollectorEnvelopeUofProto.CollectorEnvelope> producer;


    public AdapterPublisher(PulsarTemplate<CollectorEnvelopeUofProto.CollectorEnvelope> producer) {
        this.producer = producer;
    }

    public MessageId sendAdapterOutboundMessage(
            String messageKey,
            CollectorEnvelopeUofProto.CollectorEnvelope entityEnvelope) {
        try {
            return producer.newMessage(entityEnvelope)
                    .withProducerCustomizer(producerBuilder -> producerBuilder.batcherBuilder(KEY_BASED))
                    .withMessageCustomizer(messageBuilder -> messageBuilder.key(messageKey))
                    .withSchema(Schema.PROTOBUF(CollectorEnvelopeUofProto.CollectorEnvelope.class))
                    .send();
        } catch (Exception e) {
            System.out.println("Message failed to be sent to Pulsar topic. Message ID: " + messageKey + ". Error: " + e.getMessage());
            throw new PulsarException("Failed to send message to pulsar. Message ID: " + messageKey, e);
        }
    }
}
