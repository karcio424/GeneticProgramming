package Interpreter.Variables;

public class WrongProgramException extends RuntimeException {
    public WrongProgramException(String message) {
        super(message);
    }
}
