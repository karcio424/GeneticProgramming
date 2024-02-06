package Interpreter.Variables;

//import EvolutionUtils.Program;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;
import Program.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        inputFile.close();
    }

    public AntlrProgram(int maxCount, ArrayList<Integer> inputVector){
        maxOperationCount = maxCount;
        programOutput = new ArrayList<>();
        currentIndex = 0;
        didProgramFail = false;
        inputList= new ArrayList<>();
//        System.out.println(ContextTable.variables);
        ContextTable.reset();
//        System.out.println(ContextTable.variables);

//        System.out.println("INPUT-LIST: " + inputVector);
        inputList = inputVector;
//        System.out.println("INPUT-LIST: " + inputList);
    }

    @Override
    public Main visitProgram(GPprojectParser.ProgramContext ctx)  {
        AntlrStatement statementVisitor = new AntlrStatement();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            try {
                //odwiedzamy kazdy statement po kolei
//                System.out.println("ITERACJA:"+i);
                statementVisitor.visit(ctx.getChild(i));
//                System.out.println("NEW-CONTEXT:"+ContextTable.variables);
//                System.out.println();
            } catch (RuntimeException exception) {
                System.out.println("Program couldn't evaluate");
                System.out.println("CURRENT OUTPUT: " + programOutput);
                didProgramFail = true;
                break;
            }
        }
//        System.out.println(ContextTable.variables);

        return null;
    }
}
