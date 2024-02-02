package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;


public class AntlrAssignmentStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitAssignmentStatement(GPprojectParser.AssignmentStatementContext ctx) {
//        System.out.println(ctx);
        AntlrExpression expressionVisitor = new AntlrExpression();
        if (ctx.expression() != null) {
            // Assignment statement: ID '=' expression
//            System.out.println("CONTEXT:"+ContextTable.variables+" "+
//                    ctx.ID().getText()+" "+
//                    ctx.expression().getText());
            Statement value = expressionVisitor.visit(ctx.expression());
            String variableName = ctx.ID().getText();
            if (value instanceof BoolFactor) {
                ContextTable.addVariable(variableName, ((BoolFactor) value).value);
            } else {
                ContextTable.addVariable(variableName, ((Factor) value).value);
            }
//            System.out.println("NEW-CONTEXT:"+ContextTable.variables);
//        } else if (ctx.inputStatement() != null) {
//            System.out.println("INPUT HANDLER");
//            AntlrProgram programVisitor = new AntlrProgram();
//            programVisitor.visit(ctx.inputStatement().program());
        }
         return null;
    }
}
