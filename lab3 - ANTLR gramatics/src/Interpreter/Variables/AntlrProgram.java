package Interpreter.Variables;

//import EvolutionUtils.Program;
import Program.Main;
import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class AntlrProgram extends GPprojectBaseVisitor<Main> {

    public static ArrayList<Object> programOutput = new ArrayList<>();
    public static boolean didProgramFail = false;
    public static Scanner inputFile;
    static int maxOperationCount;

    public AntlrProgram(String inputFileName, int maxCount){
        maxOperationCount = maxCount;
        programOutput = new ArrayList<>();
//        VariablesTable.reset();
        didProgramFail = false;

        File inFile = new File("target/" + inputFileName);
//        File inFile = new File(inputFileName);
        try {
            inputFile = new Scanner(inFile);
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
                System.out.println("ITERACJA:"+i);
                statementVisitor.visit(ctx.getChild(i));
                System.out.println();
            } catch (RuntimeException exception) {
//                System.out.println("Program couldn't evaluate");
                didProgramFail = true;
                break;
            }
        }
//        System.out.println(ContextTable.variables);
        inputFile.close();
        return null;
    }
}
