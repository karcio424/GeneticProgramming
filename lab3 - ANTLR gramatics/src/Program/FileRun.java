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
        InterpreterInterface interpreterInterface = new InterpreterInterface(maxOperations);
        ArrayList<Object> test = InterpreterInterface.evaluateProgram(program, "input.txt", maxOperations);
        System.out.println(test);

        ArrayList<Object[]> expected = new ArrayList<>();
//        ArrayList<Object[]> expected = new ArrayList<>(Array.asList(1,2,3,4,5);
        ArrayList<int[]> parameters = new ArrayList<>();
        int ilosc=1;
        double fitness = InterpreterInterface.evaluateFitness(test, expected,ilosc);
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