package com.krmplov.storage.mapper;

import com.krmplov.storage.dto.AccountChangesDto;
import com.krmplov.storage.dto.ClientChangesDto;
import com.krmplov.storage.model.AccountChanges;
import com.krmplov.storage.model.ClientChanges;

public class ClientMapper {
    public static ClientChanges toEntity(ClientChangesDto clientChangesDto) {
        ClientChanges clientChanges = new ClientChanges();
        clientChanges.setClientId(clientChangesDto.getClientId());
        clientChanges.setMessage(clientChangesDto.getMessage());

        return clientChanges;
    }
}
