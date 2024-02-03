package Program;

import Interpreter.GPprojectLexer;
import Interpreter.GPprojectParser;
import Interpreter.Variables.AntlrProgram;
import Interpreter.InterpreterInterface;
import Interpreter.Variables.ContextTable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

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
        InterpreterInterface interpreterInterface = new InterpreterInterface(100);
        ArrayList<Object> test = interpreterInterface.evaluateProgram(program, "input.txt");
        System.out.println(test);

        ArrayList<Object> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> parameters = new ArrayList<>(Arrays.asList(100,10));
        double fitness = InterpreterInterface.evaluateFitness(test, expected,parameters);
        System.out.println(fitness);

//        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(program));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        GPprojectParser parser = new GPprojectParser(tokens);
//        for (Token token : tokens.getTokens()) {
//            System.out.println(token.getText() + " -> " + GPprojectLexer.VOCABULARY.getSymbolicName(token.getType()));
//        }
//
//        try {
//            ParseTree tree = parser.program();
//            AntlrProgram programVisitor = new AntlrProgram("input.txt", 100);
//            programVisitor.visit(tree);
//            System.out.println("PROGRAM FAILED?:"+AntlrProgram.didProgramFail);
////            System.out.println(ContextTable.variables);
//            System.out.println(AntlrProgram.programOutput);
//        } catch (RuntimeException e) {
//            System.out.println("BŁAD"); // Program zawiera błąd składniowy
//        }

    }
}