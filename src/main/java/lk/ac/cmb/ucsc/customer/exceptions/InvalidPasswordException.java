package lk.ac.cmb.ucsc.customer.exceptions;

import lk.ac.cmb.ucsc.customer.dtos.Profile;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(Profile profile) {
        super("Invalid password for user: '" + profile.getUsername() + "'");
    }
}
