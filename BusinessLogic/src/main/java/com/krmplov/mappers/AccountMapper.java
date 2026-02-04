package com.krmplov.mappers;

import com.krmplov.createRequest.CreateAccountRequest;
import com.krmplov.dto.AccountDto;
import com.krmplov.models.Account;
import com.krmplov.models.User;

public class AccountMapper {
    public static Account toEntity(CreateAccountRequest account) {
        Account accountEntity = new Account();
        accountEntity.set_balance(0);
        accountEntity.set_user(new User());
        accountEntity.get_user().set_login(account.get_user());

        return accountEntity;
    }

    public static AccountDto toDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.set_balance(account.get_balance());
        accountDto.set_user(account.get_user().get_login());
        accountDto.set_id(account.get_id());

        return accountDto;
    }
}
