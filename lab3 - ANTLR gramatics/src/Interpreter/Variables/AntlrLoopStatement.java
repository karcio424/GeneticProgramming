package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrLoopStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitLoopStatement(GPprojectParser.LoopStatementContext ctx) {
        AntlrExpression expressionVisitor = new AntlrExpression();
        AntlrBlockStatement blockStatementVisitor = new AntlrBlockStatement();

        int range = ((Factor) expressionVisitor.visit(ctx.expression())).value;
        System.out.println("LOOP-INSIDE range"+range);

        for (int i = 0; i < range; i++)
            blockStatementVisitor.visit(ctx.blockStatement());

        return null;
    }
}
