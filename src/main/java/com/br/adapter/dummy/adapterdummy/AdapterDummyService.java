package com.br.adapter.dummy.adapterdummy;

import flutter.gstt.data.betradar_uof.CollectorEnvelopeUofProto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdapterDummyService {
    private final AdapterPublisher producer;

    public AdapterDummyService(AdapterPublisher producer) {
        this.producer = producer;
    }

    public void readJsonAndSend(String resourceFileName) throws Exception {
        List<CollectorEnvelopeUofProto.CollectorEnvelope> messages = JsonFileReader.processJsonFileFromResources(resourceFileName);

        for (CollectorEnvelopeUofProto.CollectorEnvelope message : messages) {
            String messageKey = message.getExternalId();
            producer.sendAdapterOutboundMessage(messageKey, message);
            System.out.println("Message sent with key: " + messageKey);
        }

    }
}
