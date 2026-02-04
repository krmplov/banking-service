package com.krmplov.storage.mapper;

import com.krmplov.storage.dto.AccountChangesDto;
import com.krmplov.storage.model.AccountChanges;

public class AccountMapper {

    public static AccountChanges toEntity(AccountChangesDto accountChangesDto) {
        AccountChanges accountChanges = new AccountChanges();
        accountChanges.setAccountId(accountChangesDto.getAccountId());
        accountChanges.setMessage(accountChangesDto.getMessage());

        return accountChanges;
    }

}
