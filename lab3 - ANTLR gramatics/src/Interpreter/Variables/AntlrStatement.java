package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

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
