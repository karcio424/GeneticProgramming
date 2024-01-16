package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitLoopStatement(GPprojectParser.LoopStatementContext ctx) {
        AntlrLoopStatement loopStatementVisitor = new AntlrLoopStatement();
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                "The program has exceeded the allowable limit for operations.");
        return loopStatementVisitor.visit(ctx.getChild(0));
    }

    @Override
    public Statement visitConditionalStatement(GPprojectParser.ConditionalStatementContext ctx) {
        AntlrConditionalStatement conditionalVisitor = new AntlrConditionalStatement();
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return conditionalVisitor.visit(ctx.getChild(0));
    }

    @Override
    public Statement visitBlockStatement(GPprojectParser.BlockStatementContext ctx) {
        AntlrBlockStatement blockStatementVisitor = new AntlrBlockStatement();
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return blockStatementVisitor.visit(ctx.getChild(0));
    }

    @Override
    public Statement visitAssignmentStatement(GPprojectParser.AssignmentStatementContext ctx) {
        AntlrAssignmentStatement assignStatementVisitor = new AntlrAssignmentStatement();
        if (AntlrProgram.maxOperationCount-- <= 0)
            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
                    "The program has exceeded the allowable limit for operations.");
        return assignStatementVisitor.visit(ctx.getChild(0));
    }

//    TODO: think about this statements
//    @Override
//    public Statement visitIoStatement(GPprojectParser.IoStatementContext ctx) {
//        AntlrInput inputVisitor = new AntlrInput();
//        AntlrOutput outputVisitor = new AntlrOutput();
//        if (AntlrProgram.maxOperationCount-- <= 0)
//            throw new WrongProgramException("RuntimeException: Maximum Operations Exceeded\n" +
//                "The program has exceeded the allowable limit for operations.");
//
//        return outputVisitor.visit(ctx.getChild(0));
//    }
}
