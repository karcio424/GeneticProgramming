package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrConditionalStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitConditionalStatement(GPprojectParser.ConditionalStatementContext ctx) {
//        AntlrBoolStatement boolStatementVisitor = new AntlrBoolStatement();
        //TODO: przemyśleć co w IF-ie (czy może być expression na pewno)

        AntlrExpression expressionVisitor = new AntlrExpression();
        AntlrBlockStatement blockStatementVisitor = new AntlrBlockStatement();

//        if (boolStatementVisitor.visit(ctx.boolStatement()).satisfied) blockStatementVisitor.visit(ctx.blockStatement(0));
        //TODO: Zmienić
        if (expressionVisitor.visit(ctx.expression())!=null) blockStatementVisitor.visit(ctx.blockStatement(0));
        else if (ctx.blockStatement(1) != null)
                blockStatementVisitor.visit(ctx.blockStatement(1));

        return null;
    }
}
