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

    public static ArrayList<Integer> programOutput = new ArrayList<>();
    public static boolean didProgramFail = false;
    public static Scanner inputFile;
    static int maxOperationCount;

    public AntlrProgram(String inputFileName, int maxCount){
        maxOperationCount = maxCount;
        programOutput = new ArrayList<>();
//        VariablesTable.reset();
        didProgramFail = false;

        File inFile = new File("target/" + inputFileName);
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
                statementVisitor.visit(ctx.getChild(i));
            } catch (RuntimeException exception) {
//                System.out.println("Program couldn't evaluate");
                didProgramFail = true;
                break;
            }
        }

        inputFile.close();
        return null;
    }
}
