package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class AntlrStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitLoopStatement(GPprojectParser.LoopStatementContext ctx) {
        AntlrLoopStatement loopStatementVisitor = new AntlrLoopStatement();
        System.out.println("LOOP:"+ ctx.getText());
//        System.out.println("ILE:"+AntlrProgram.maxOperationCount);
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                "The program has exceeded the allowable limit for operations.");
        return loopStatementVisitor.visit(ctx);
    }

    @Override
    public Statement visitConditionalStatement(GPprojectParser.ConditionalStatementContext ctx) {
        AntlrConditionalStatement conditionalVisitor = new AntlrConditionalStatement();
        System.out.println("CONDITIONAL:"+ctx.getText());
//        System.out.println("ILE:"+AntlrProgram.maxOperationCount);
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return conditionalVisitor.visit(ctx);
    }

    @Override
    public Statement visitBlockStatement(GPprojectParser.BlockStatementContext ctx) {
        AntlrBlockStatement blockStatementVisitor = new AntlrBlockStatement();
        System.out.println("BLOCK:"+ctx.getText());
//        System.out.println("ILE:"+AntlrProgram.maxOperationCount);
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return blockStatementVisitor.visit(ctx);
    }

    @Override
    public Statement visitAssignmentStatement(GPprojectParser.AssignmentStatementContext ctx) {
        AntlrAssignmentStatement assignStatementVisitor = new AntlrAssignmentStatement();
        System.out.println("ASSIGN:"+ctx.getText());
//        System.out.println("ILE:"+AntlrProgram.maxOperationCount);
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return assignStatementVisitor.visit(ctx);
    }

//    TODO: think about input statements
    @Override
    public Statement visitInputStatement(GPprojectParser.InputStatementContext ctx){
        System.out.println("INPUT HANDLER");
        return null;
    }

    @Override
    public Statement visitOutputStatement(GPprojectParser.OutputStatementContext ctx) {
          List<TerminalNode> idNodes = ctx.ID();

            if (idNodes.isEmpty()) {
                throw new WrongProgramException("RuntimeException: At least one ID is required\n" +
                        "in the output statement!");
            } else {
//                System.out.print("Outputs: ");
                for (TerminalNode idNode : idNodes) {
//                    if (i > 0) {
//                        System.out.print(", ");
//                    }
                    Object variableValue = ContextTable.getVariableValue(idNode.getText());
                    if (variableValue instanceof Boolean) {
                        // If the value is a boolean, add it directly to the programOutput list
                        AntlrProgram.programOutput.add((Boolean) variableValue);
                    } else if (variableValue instanceof Integer) {
                        // If the value is an integer, add it as an integer to the programOutput list
                        AntlrProgram.programOutput.add((Integer) variableValue);
                    } else {
                        // Handle other types as needed
                        System.out.println("Unsupported variable type for ID: " + idNode.getText());
                        //                    System.out.print(ContextTable.getVariableValue(idNodes.get(i).getText()));
                    }
                    System.out.println();
                }
            }
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                "The program has exceeded the allowable limit for operations.");
        return null;
//        return outputVisitor.visit(ctx.getChild(0));
    }
}
