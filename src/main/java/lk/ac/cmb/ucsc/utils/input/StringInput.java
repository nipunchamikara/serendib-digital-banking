package lk.ac.cmb.ucsc.utils.input;

import lk.ac.cmb.ucsc.customer.validator.Validator;

import java.util.Scanner;

public class StringInput extends Input<String> {

    public StringInput(String title, Validator<String> validator) {
        super(title, validator);
    }

    public StringInput(String title) {
        super(title);
    }

    @Override
    protected String readValue(Scanner scanner) {
        return scanner.nextLine().strip();
    }
}
