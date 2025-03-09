package lk.ac.cmb.ucsc.customer.exceptions;

import lk.ac.cmb.ucsc.customer.dtos.Profile;

public class ProfileLockedException extends RuntimeException {
    public ProfileLockedException(Profile profile) {
        super("Profile is locked for user: '" + profile.getUsername() + "'. Contact your nearest branch for more " +
                "information.");
    }
}
