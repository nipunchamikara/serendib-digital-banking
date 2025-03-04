package lk.ac.cmb.ucsc.customer.repositories;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;

public interface CASAAccountStore {
    CASAAccount getAccount(String username);

    void saveAccount(CASAAccount profile);
}
