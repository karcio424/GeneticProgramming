package Program;

import Interpreter.InterpreterInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileRun {
    public static void main(String[] args) {
        String program = null;
        try {
            program = Files.readString(Paths.get("target/input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to read program from file.");
            System.exit(1);
        }
        int maxOperations = 100;
        ArrayList<Object> test = InterpreterInterface.evaluateProgram(program, maxOperations);
        System.out.println(test);

        ArrayList<Object[]> expected = new ArrayList<>();
        int ilosc = 1;
        double fitness = InterpreterInterface.evaluateFitness(test, expected, ilosc);
        System.out.println(fitness);
    }
}