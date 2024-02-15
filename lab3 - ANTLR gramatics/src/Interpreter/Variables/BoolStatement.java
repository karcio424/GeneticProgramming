package Interpreter.Variables;

public class BoolStatement {
    public boolean satisfied;

    public BoolStatement(Statement variable) {
        if (variable instanceof Factor) {
            satisfied = (((Factor) variable).value != 0);
        } else
            satisfied = false;
    }

    public BoolStatement() {
        satisfied = false;
    }

}