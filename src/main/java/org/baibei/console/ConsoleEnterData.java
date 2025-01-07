package org.baibei.console;

import java.util.ArrayList;
import java.util.List;

public class ConsoleEnterData {

    private List<String> inputs;

    public ConsoleEnterData(int size) {
        inputs = new ArrayList<String>(size);
    }

    public List<String> getInputs() {
        return inputs;
    }

    public void setInputs(List<String> inputs) {
        this.inputs = inputs;
    }

    public void addInput(String input) {
        inputs.add(input);
    }
}
