package lk.ac.cmb.ucsc.utils.input;

import lk.ac.cmb.ucsc.customer.validator.Validator;

import java.util.Scanner;

public abstract class Input<T> {
    private final String title;
    private final Validator<T> validator;

    public Input(String title, Validator<T> validator) {
        this.title = title;
        this.validator = validator;
    }

    public Input(String title) {
        this.title = title;
        this.validator = null;
    }

    abstract protected T readValue(final Scanner scanner);

    public T promptUser(final Scanner scanner) {
        System.out.print("\n" + title + ": ");

        T value;
        try {
            value = readValue(scanner);
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            return promptUser(scanner);
        }

        if (validator == null) return value;
        if (!validator.validate(value)) {
            System.out.println("Invalid input. Please try again.");
            return promptUser(scanner);
        } else {
            return value;
        }
    }
}
