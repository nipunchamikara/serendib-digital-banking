package lk.ac.cmb.ucsc.utils.selection;

import lk.ac.cmb.ucsc.utils.input.IntegerInput;

import java.util.List;
import java.util.Scanner;

public class MenuSelection {
    private final String title;
    private final List<String> options;

    public MenuSelection(String title, List<String> options) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (options == null || options.size() < 2) {
            throw new IllegalArgumentException("Options cannot be null or less than 2");
        }
        this.title = title;
        this.options = options;
    }

    public int promptUser(final Scanner scanner) {
        System.out.println("\n" + title);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        final var selection = new IntegerInput("Enter your selection").promptUser(scanner);
        if (selection < 1 || selection > options.size()) {
            System.out.println("Invalid selection");
            return promptUser(scanner);
        }
        return selection;
    }
}
