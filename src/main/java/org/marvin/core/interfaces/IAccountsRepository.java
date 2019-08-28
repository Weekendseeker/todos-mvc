package org.marvin.core.interfaces;

import org.marvin.core.models.Account;

import java.util.List;

public interface IAccountsRepository<T extends Account> {

        Account findByLogin(String login);
        long createAccount(Account account);

}
