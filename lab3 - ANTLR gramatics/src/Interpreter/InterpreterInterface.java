package Interpreter;

import Interpreter.Variables.AntlrProgram;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;

import static org.antlr.v4.runtime.CharStreams.fromString;

public class InterpreterInterface {

    private static int maxOperationCount = 0;
    private static boolean didProgramFail;
    public static int testsCasesCount;

    public InterpreterInterface(int maxOperationCount) {
        InterpreterInterface.maxOperationCount = maxOperationCount;
        InterpreterInterface.didProgramFail = false;
    }

    public static ArrayList<Object> evaluateProgram(String program, String inputFileName, int maxOperations) {
//        ANTLRErrorListener errorListener = new BaseErrorListener();
        GPprojectParser parser = getParser(program);
//        parser.addErrorListener(errorListener);
        ParseTree antlrAST = parser.program();
//        System.out.println("------------- Program: -------------");
//        System.out.println(program);
//        System.out.println("------------------------------------");
        AntlrProgram programVisitor = new AntlrProgram(maxOperations);
        programVisitor.visit(antlrAST);
        InterpreterInterface.didProgramFail = AntlrProgram.didProgramFail;

        return AntlrProgram.programOutput;
    }

    public static ArrayList<Object> evaluateProgram(String program, ArrayList<Integer> inputArray, int maxOperations) {
//        ANTLRErrorListener errorListener = new BaseErrorListener();
        GPprojectParser parser = getParser(program);
//        parser.addErrorListener(errorListener);
        ParseTree antlrAST = parser.program();
//        System.out.println("------------- Program: -------------");
//        System.out.println(program);
//        System.out.println("------------------------------------");
//        System.out.println("--------");
        AntlrProgram programVisitor = new AntlrProgram(maxOperations, inputArray);
        programVisitor.visit(antlrAST);
//        System.out.println("--------");
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
//        parser.addErrorListener();
        return parser;
    }

    public static double evaluateFitness(ArrayList<Object> actualOutput, ArrayList<Object[]> expectedOutput, int testCases) {
        testsCasesCount = testCases;
        int actualLength = actualOutput.size();
        int expectedLength;
        int difference=0;
        for(int i=0;i<testsCasesCount;i++){
            expectedLength = expectedOutput.get(i).length;
            //TODO: jak okreslic parametry dlugosci
            int lengthDifference = expectedLength - actualLength;
            if (lengthDifference > 0) {
                difference += lengthDifference*100;
            } else {
                difference += calculateDistance(actualOutput, expectedOutput.get(i)) - lengthDifference*100;//*paramaters.get(1);
            }
        }
        return difference;
    }

    public static double evaluateFitness(ArrayList<Object> actualOutput, Object[] expectedOutput) {
        int actualLength = actualOutput.size();
        int expectedLength = expectedOutput.length;
        int lengthDifference = expectedLength - actualLength;
        if (lengthDifference > 0) {
            return lengthDifference*100;
        } else {
            return calculateDistance(actualOutput, expectedOutput) - lengthDifference*100;//*paramaters.get(1);
        }

    }
//
//    private static int calculateDistance(ArrayList<Object> actualOutput, ArrayList<Object> expectedOutput) {
//        int distance = 0;
//        for (int i = 0; i < expectedOutput.size(); i++) {
//            Object expected = expectedOutput.get(i);
//            Object actual = actualOutput.get(i);
//            if (expected instanceof Integer && actual instanceof Integer) {
//                distance += Math.abs((Integer) expected - (Integer) actual);
//            } else {
//                System.out.println("NIE INTEGER");
//                distance+=100000;
//            }
//        }
//        return distance;
//    }

    private static int calculateDistance(ArrayList<Object> actualOutput, Object[] expectedOutput) {
        int distance = 0;
        for (int i = 0; i < expectedOutput.length; i++) {
            Object expected = expectedOutput[i];
            Object actual = actualOutput.get(i);
            if (expected instanceof Integer && actual instanceof Integer) {
                distance += Math.abs((Integer) expected - (Integer) actual);
            } else {
                System.out.println("NIE INTEGER");
                distance+=100000;
            }
        }
        return distance;
    }

    public static boolean getDidProgramFail(){
        return didProgramFail;
    }


}
