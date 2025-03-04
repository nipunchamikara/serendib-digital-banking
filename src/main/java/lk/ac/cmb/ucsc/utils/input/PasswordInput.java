package lk.ac.cmb.ucsc.utils.input;

import lk.ac.cmb.ucsc.customer.validator.Validator;

import java.util.Scanner;

public class PasswordInput extends Input<String> {
    public PasswordInput(String title, Validator<String> validator) {
        super(title, validator);
    }

    public PasswordInput(String title) {
        super(title);
    }

    @Override
    protected String readValue(Scanner scanner) {
        try {
            // If running in an IDE, System.console() will return null
            return new String(System.console().readPassword());
        } catch (NullPointerException e) {
            return scanner.nextLine().strip();
        }
    }
}
