package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrConditionalStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitConditionalStatement(GPprojectParser.ConditionalStatementContext ctx) {
        AntlrExpression expressionVisitor = new AntlrExpression();
        AntlrBlockStatement blockStatementVisitor = new AntlrBlockStatement();
        Statement ifValue = (expressionVisitor.visit(ctx.expression()));

        if (ifValue instanceof BoolFactor) {
            if (((BoolFactor) ifValue).value)
                blockStatementVisitor.visit(ctx.blockStatement(0));
            else if (ctx.blockStatement(1) != null)
                blockStatementVisitor.visit(ctx.blockStatement(1));
        } else if (ifValue instanceof Factor) {
            if (((Factor) ifValue).value > 0)
                blockStatementVisitor.visit(ctx.blockStatement(0));
            else if (ctx.blockStatement(1) != null)
                blockStatementVisitor.visit(ctx.blockStatement(1));
        } else {
            System.out.println("IF SIĘ NIE WYKONAŁ!!!");
        }
        return null;
    }
}
