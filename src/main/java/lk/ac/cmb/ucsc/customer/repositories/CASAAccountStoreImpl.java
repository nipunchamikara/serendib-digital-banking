package lk.ac.cmb.ucsc.customer.repositories;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;

import java.util.HashMap;
import java.util.Map;

public enum CASAAccountStoreImpl implements CASAAccountStore {
    INSTANCE;

    private final Map<String, CASAAccount> accountStore = new HashMap<>();

    public void saveAccount(CASAAccount account) {
        accountStore.put(account.accountNumber().toLowerCase(), account);
    }

    public CASAAccount getAccount(String username) {
        return accountStore.get(username.toLowerCase());
    }
}
