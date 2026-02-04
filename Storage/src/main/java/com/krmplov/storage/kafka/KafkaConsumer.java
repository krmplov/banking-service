package com.krmplov.storage.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.krmplov.storage.dto.AccountChangesDto;
import com.krmplov.storage.dto.ClientChangesDto;
import com.krmplov.storage.service.AccountChangesService;
import com.krmplov.storage.service.ClientChangesService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaConsumer {
    private final ClientChangesService clientChangesService;
    private final AccountChangesService accountChangesService;


    @KafkaListener(topics = "client-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeClient(ConsumerRecord<String, JsonNode> record) {
        JsonNode node = record.value();
        String message = node.has("message") ? node.get("message").asText() : "";

        ClientChangesDto clientChangesDto = new ClientChangesDto();
        clientChangesDto.setMessage(message);
        clientChangesDto.setClientId(record.key());

        clientChangesService.save(clientChangesDto);
    }

    @KafkaListener(topics = "account-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAccount(ConsumerRecord<String, JsonNode> record) {
        JsonNode node = record.value();
        String message = node.has("message") ? node.get("message").asText() : "";

        AccountChangesDto accountChangesDto = new AccountChangesDto();
        accountChangesDto.setMessage(message);
        accountChangesDto.setAccountId(record.key());

        accountChangesService.save(accountChangesDto);
    }


}
