package com.br.adapter.dummy.adapterdummy;

import flutter.gstt.data.generic_external_entity.GenericExternalEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdapterDummyService {
    private final AdapterPublisher producer;

    public void readJsonAndSend(String resourceFileName) throws Exception {
        List<GenericExternalEntity.EntityEnvelope> messages = JsonFileReader.processJsonFileFromResources(resourceFileName);

        for (GenericExternalEntity.EntityEnvelope message : messages) {
            String messageKey = message.getEntity().getExternalId();
            producer.sendAdapterOutboundMessage(messageKey, message);
            log.info("Message with ID " + messageKey + " sent to Pulsar topic.");
        }

    }
}
