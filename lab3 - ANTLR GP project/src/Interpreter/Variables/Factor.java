package Interpreter.Variables;

public class Factor extends Statement {
    int value;
    boolean bool;

    public Factor(int value) {
        this.value = value;
    }

    public Factor(boolean value) {
        this.bool = value;
    }
}
