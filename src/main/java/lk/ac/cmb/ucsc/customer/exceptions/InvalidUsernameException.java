package lk.ac.cmb.ucsc.customer.exceptions;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException(String username) {
        super("Invalid username: '" + username + "'");
    }
}
