package lk.ac.cmb.ucsc.utils.input;

import lk.ac.cmb.ucsc.customer.validator.Validator;

import java.util.Scanner;

public class IntegerInput extends Input<Integer> {

    public IntegerInput(String title, Validator<Integer> validator) {
        super(title, validator);
    }

    public IntegerInput(String title) {
        super(title);
    }

    @Override
    protected Integer readValue(Scanner scanner) {
        return Integer.parseInt(scanner.nextLine());
    }
}
