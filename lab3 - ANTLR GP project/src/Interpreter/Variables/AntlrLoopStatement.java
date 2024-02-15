package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrLoopStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitLoopStatement(GPprojectParser.LoopStatementContext ctx) {
        int MAX_RANGE = 10;
        int range = 0;
        AntlrExpression expressionVisitor = new AntlrExpression();
        AntlrBlockStatement blockStatementVisitor = new AntlrBlockStatement();

        Statement rangeValue = expressionVisitor.visit(ctx.expression());
        if (!(rangeValue == null)) {
            if (!(rangeValue instanceof BoolFactor)) {
                if (rangeValue instanceof Factor) {
                    range = Math.min(((Factor) rangeValue).value, MAX_RANGE);
                } else {
                    System.out.println("LOOP SIĘ NIE WYKONAŁ!!!");
                }
            } else {
                if (!((BoolFactor) rangeValue).value) {
                    return null;
                }
                range = MAX_RANGE;
            }
            for (int i = 0; i < range; i++) {
                blockStatementVisitor.visit(ctx.blockStatement());
            }
        }

        return null;
    }
}
