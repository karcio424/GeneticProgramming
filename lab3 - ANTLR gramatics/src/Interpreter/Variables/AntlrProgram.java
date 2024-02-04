package Interpreter.Variables;

//import EvolutionUtils.Program;
import Program.Main;
import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class AntlrProgram extends GPprojectBaseVisitor<Main> {

    public static ArrayList<Object> programOutput = new ArrayList<>();
    public static boolean didProgramFail = false;
    public static Scanner inputFile;
    static int maxOperationCount;
    static int currentIndex;
    public static List<Integer> inputList = new ArrayList<>();

    public AntlrProgram(int maxCount){
        maxOperationCount = maxCount;
        programOutput = new ArrayList<>();
        currentIndex = 0;
//        VariablesTable.reset();
        didProgramFail = false;
        inputList.clear();
        ContextTable.variables.clear();

        File inFile = new File("target/input.txt");
//        File inFile = new File(inputFileName);
        try {
            inputFile = new Scanner(inFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            File dataFile = new File("target/test_values.txt"); //+ dataFileName); // Ścieżka do pliku z danymi
            Scanner scanner = new Scanner(dataFile);
            while (scanner.hasNextInt()) {
                inputList.add(scanner.nextInt());
            }
            System.out.println(inputList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Main visitProgram(GPprojectParser.ProgramContext ctx)  {
        AntlrStatement statementVisitor = new AntlrStatement();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            try {
                //odwiedzamy kazdy statement po kolei
//                System.out.println("ITERACJA:"+i);
                statementVisitor.visit(ctx.getChild(i));
                System.out.println("NEW-CONTEXT:"+ContextTable.variables);
//                System.out.println();
            } catch (RuntimeException exception) {
                System.out.println("Program couldn't evaluate");
                System.out.println("CURRENT OUTPUT: " + programOutput);
                didProgramFail = true;
                break;
            }
        }
//        System.out.println(ContextTable.variables);
        inputFile.close();
        return null;
    }
}
