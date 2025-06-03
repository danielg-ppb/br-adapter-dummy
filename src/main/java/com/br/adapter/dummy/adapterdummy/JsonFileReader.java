package com.br.adapter.dummy.adapterdummy;

import com.google.protobuf.util.JsonFormat;
import flutter.gstt.data.generic_external_entity.GenericExternalEntity;
import org.apache.pulsar.shade.com.fasterxml.jackson.databind.JsonNode;
import org.apache.pulsar.shade.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader {
    public static List<GenericExternalEntity.EntityEnvelope> processJsonFileFromResources(String resourceFileName) throws Exception {
        ClassPathResource resource = new ClassPathResource(resourceFileName);
        List<GenericExternalEntity.EntityEnvelope> entities = new ArrayList<>();

        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(inputStream);

            if (!rootNode.isArray()) {
                throw new IllegalArgumentException("Expected a JSON array");
            }

            for (JsonNode node : rootNode) {
                GenericExternalEntity.EntityEnvelope.Builder builder = GenericExternalEntity.EntityEnvelope.newBuilder();
                JsonFormat.parser().merge(node.toString(), builder);
                entities.add(builder.build());
            }
        }
        return entities;
    }
}
