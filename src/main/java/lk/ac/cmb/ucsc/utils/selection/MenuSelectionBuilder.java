package lk.ac.cmb.ucsc.utils.selection;

import java.util.ArrayList;
import java.util.List;

public class MenuSelectionBuilder {
    private final List<String> options = new ArrayList<>();
    private String title;

    public MenuSelectionBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public MenuSelectionBuilder addOption(String option) {
        options.add(option);
        return this;
    }

    public MenuSelection build() {
        return new MenuSelection(title, options);
    }
}
