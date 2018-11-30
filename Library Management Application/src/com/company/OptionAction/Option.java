package com.company.OptionAction;

public class Option {

    private String label;
    private Runnable action;

    public Option(String label, Runnable action) {
        this.label = label;
        this.action = action;
    }

    public void doAction() {
        action.run();
    }

    @Override
    public String toString() {
        return label;
    }
}
