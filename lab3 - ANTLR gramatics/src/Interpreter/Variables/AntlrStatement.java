package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class AntlrStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitLoopStatement(GPprojectParser.LoopStatementContext ctx) {
        AntlrLoopStatement loopStatementVisitor = new AntlrLoopStatement();
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return loopStatementVisitor.visit(ctx);
    }

    @Override
    public Statement visitConditionalStatement(GPprojectParser.ConditionalStatementContext ctx) {
        AntlrConditionalStatement conditionalVisitor = new AntlrConditionalStatement();
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return conditionalVisitor.visit(ctx);
    }

    @Override
    public Statement visitBlockStatement(GPprojectParser.BlockStatementContext ctx) {
        AntlrBlockStatement blockStatementVisitor = new AntlrBlockStatement();
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return blockStatementVisitor.visit(ctx);
    }

    @Override
    public Statement visitAssignmentStatement(GPprojectParser.AssignmentStatementContext ctx) {
        AntlrAssignmentStatement assignStatementVisitor = new AntlrAssignmentStatement();
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return assignStatementVisitor.visit(ctx);
    }

    @Override
    public Statement visitInputStatement(GPprojectParser.InputStatementContext ctx) {
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
            for (TerminalNode idNode : idNodes) {
                Object variableValue = ContextTable.getVariableValue(idNode.getText());
                if (variableValue instanceof Boolean) {
                    AntlrProgram.programOutput.add(variableValue);
                } else if (variableValue instanceof Integer) {
                    AntlrProgram.programOutput.add(variableValue);
                }
            }
        }
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return null;
    }
}
