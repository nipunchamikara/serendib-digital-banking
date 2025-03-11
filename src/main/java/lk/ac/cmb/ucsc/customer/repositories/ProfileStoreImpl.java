package lk.ac.cmb.ucsc.customer.repositories;

import lk.ac.cmb.ucsc.customer.dtos.CASAAccount;
import lk.ac.cmb.ucsc.customer.dtos.Profile;

import java.util.HashMap;
import java.util.Map;

public enum ProfileStoreImpl implements ProfileStore {
    INSTANCE;

    private final Map<String, Profile> profileStore = new HashMap<>();
    private final Map<String, Profile> accountToProfile = new HashMap<>();

    public void saveProfile(Profile profile) {
        profileStore.put(profile.getUsername().toLowerCase(), profile);
        accountToProfile.put(profile.getAccount().getAccountNumber(), profile);
    }

    public Profile getProfile(String username) {
        return profileStore.get(username.toLowerCase());
    }

    public Profile getProfile(CASAAccount account) {
        return accountToProfile.get(account.getAccountNumber());
    }
}
