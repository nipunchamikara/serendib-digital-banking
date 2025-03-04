package lk.ac.cmb.ucsc.customer.repositories;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.customer.dtos.Profile;

public interface ProfileStore {
    Profile getProfile(String username);

    void saveProfile(Profile profile);

    Profile getProfile(CASAAccount accountNumber);
}
