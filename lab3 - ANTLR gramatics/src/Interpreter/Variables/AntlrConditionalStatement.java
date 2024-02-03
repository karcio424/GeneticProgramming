package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrConditionalStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitConditionalStatement(GPprojectParser.ConditionalStatementContext ctx) {
        //TODO: przemyśleć co w IF-ie (czy może być expression na pewno)
        AntlrExpression expressionVisitor = new AntlrExpression();
        AntlrBlockStatement blockStatementVisitor = new AntlrBlockStatement();
        System.out.println("IF-INSIDE:"+ctx.expression().getText()+" "+
                ctx.blockStatement(0).getText()+" "+
                ctx.blockStatement(1).getText());
        Statement ifValue = (expressionVisitor.visit(ctx.expression()));

        if (ifValue instanceof BoolFactor){
            if (((BoolFactor) ifValue).value)
                blockStatementVisitor.visit(ctx.blockStatement(0));
            else if (ctx.blockStatement(1) != null)
                    blockStatementVisitor.visit(ctx.blockStatement(1));
        }
        else if (ifValue instanceof Factor){
            if (((Factor) ifValue).value>0)
                blockStatementVisitor.visit(ctx.blockStatement(0));
            else if (ctx.blockStatement(1) != null)
                blockStatementVisitor.visit(ctx.blockStatement(1));
        }
        else {
            System.out.println("IF SIĘ NIE WYKONAŁ!!!");
        }
        return null;
    }
}
