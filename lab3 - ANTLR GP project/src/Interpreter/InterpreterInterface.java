package Interpreter;

import Interpreter.Variables.AntlrProgram;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;

import static org.antlr.v4.runtime.CharStreams.fromString;

public class InterpreterInterface {

    private static boolean didProgramFail;
    public static int testsCasesCount;

    public InterpreterInterface() {
        InterpreterInterface.didProgramFail = false;
    }

    public static ArrayList<Object> evaluateProgram(String program, int maxOperations) {
        GPprojectParser parser = getParser(program);
        ParseTree antlrAST = parser.program();
        AntlrProgram programVisitor = new AntlrProgram(maxOperations);
        programVisitor.visit(antlrAST);
        InterpreterInterface.didProgramFail = AntlrProgram.didProgramFail;

        return AntlrProgram.programOutput;
    }

    public static ArrayList<Object> evaluateProgram(String program, ArrayList<Integer> inputArray, int maxOperations) {
        GPprojectParser parser = getParser(program);
        ParseTree antlrAST = parser.program();
        AntlrProgram programVisitor = new AntlrProgram(maxOperations, inputArray);
        programVisitor.visit(antlrAST);
        InterpreterInterface.didProgramFail = AntlrProgram.didProgramFail;
        return AntlrProgram.programOutput;
    }


    private static GPprojectParser getParser(String program) {
        GPprojectParser parser;
        CharStream stream = fromString(program);
        GPprojectLexer lexer = new GPprojectLexer(stream);
        lexer.addErrorListener(lexer.getErrorListenerDispatch());
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new GPprojectParser(tokens);
        return parser;
    }

    public static double evaluateFitness(ArrayList<Object> actualOutput, ArrayList<Object[]> expectedOutput, int testCases) {
        testsCasesCount = testCases;
        int actualLength = actualOutput.size();
        int expectedLength;
        int difference = 0;
        for (int i = 0; i < testsCasesCount; i++) {
            expectedLength = expectedOutput.get(i).length;
            int lengthDifference = expectedLength - actualLength;
            if (lengthDifference > 0) {
                difference += lengthDifference * 1000;
            } else {
                difference += calculateDistance(actualOutput, expectedOutput.get(i)) - lengthDifference * 1000;
            }
        }
        return difference;
    }

    public static double evaluateFitness(ArrayList<Object> actualOutput, Object[] expectedOutput) {
        int actualLength = actualOutput.size();
        int expectedLength = expectedOutput.length;
        int lengthDifference = expectedLength - actualLength;
        if (lengthDifference > 0) {
            return lengthDifference * 1000;
        } else {
            return calculateDistance(actualOutput, expectedOutput) - lengthDifference * 1000;
        }

    }

    private static int calculateDistance(ArrayList<Object> actualOutput, Object[] expectedOutput) {
        int distance = 0;
        for (int i = 0; i < expectedOutput.length; i++) {
            Object expected = expectedOutput[i];
            Object actual = actualOutput.get(i);
            if (expected instanceof Integer && actual instanceof Integer) {
                distance += Math.abs((Integer) expected - (Integer) actual);
            } else {
                distance += 1000;
            }
        }
        return distance;
    }

    public static boolean getDidProgramFail() {
        return didProgramFail;
    }
}
