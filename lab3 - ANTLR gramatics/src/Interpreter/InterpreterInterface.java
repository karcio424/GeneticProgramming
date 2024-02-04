package Interpreter;

import Interpreter.Variables.AntlrProgram;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileWriter;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.antlr.v4.runtime.CharStreams.fromString;

public class InterpreterInterface {

    private final int maxOperationCount;
    public boolean didProgramFail;

    public InterpreterInterface(int maxOperationCount) {
        this.maxOperationCount = maxOperationCount;
        this.didProgramFail = false;
    }

    public ArrayList<Object> evaluateProgram(String program, String inputFileName) {
//        ANTLRErrorListener errorListener = new BaseErrorListener();
        GPprojectParser parser = getParser(program);
//        parser.addErrorListener(errorListener);
        ParseTree antlrAST = parser.program();
        System.out.println("------------- Program: -------------");
        System.out.println(program);
        System.out.println("------------------------------------");
        AntlrProgram programVisitor = new AntlrProgram(maxOperationCount);
        programVisitor.visit(antlrAST);
        this.didProgramFail = AntlrProgram.didProgramFail;

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

    public static double evaluateFitness(ArrayList<Object> actualOutput, ArrayList<Object> expectedOutput, ArrayList<Integer> paramaters) {
        int actualLength = actualOutput.size();
        int expectedLength = expectedOutput.size();

        //TODO: jak okreslic parametry dlugosci
        int lengthDifference = expectedLength - actualLength;
        if (lengthDifference > 0) {
            return lengthDifference*100;// paramaters.get(0);
        } else {
            return calculateDistance(actualOutput, expectedOutput) - lengthDifference*10;//*paramaters.get(1);
        }

//        return distance + lengthDifference;
    }

    private static int calculateDistance(ArrayList<Object> actualOutput, ArrayList<Object> expectedOutput) {
        int distance = 0;
        for (int i = 0; i < expectedOutput.size(); i++) {
            Object expected = expectedOutput.get(i);
            Object actual = actualOutput.get(i);
            //TODO: wartosci inne niz int
            if (expected instanceof Integer && actual instanceof Integer) {
                distance += Math.abs((Integer) expected - (Integer) actual);
            } else {
                System.out.println("NIE INTEGER");
            }
        }
        return distance;
    }


}
