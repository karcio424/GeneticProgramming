package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Scanner;

public class AntlrInputStatement extends GPprojectBaseVisitor<Factor> {
    @Override
    public Factor visitInputStatement(GPprojectParser.InputStatementContext ctx) {
        Scanner inputFile = AntlrProgram.inputFile;
        if (inputFile.hasNextLine()) {
            String data = inputFile.nextLine();
            int value = Integer.parseInt(data);
            return new Factor(value);
        }
        else throw new WrongProgramException("RuntimeException: Insufficient data in input file\n" +
                "The program attempted to read more numbers from the input file than are available.\n");
    }

}
