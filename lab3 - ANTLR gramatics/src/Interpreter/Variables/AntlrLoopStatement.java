package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrLoopStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitLoopStatement(GPprojectParser.LoopStatementContext ctx) {
        //TODO: ILE RÓWNY MAX RANGE?
        int MAX_RANGE = 100;
        int range = 0;
        AntlrExpression expressionVisitor = new AntlrExpression();
        AntlrBlockStatement blockStatementVisitor = new AntlrBlockStatement();

        Statement rangeValue = expressionVisitor.visit(ctx.expression());
        if (!(rangeValue instanceof BoolFactor)) {
            if (rangeValue instanceof Factor) {
                range = Math.min(((Factor) rangeValue).value, MAX_RANGE);
            } else {
                System.out.println("LOOP SIĘ NIE WYKONAŁ!!!");
            }
        } else {
            range = MAX_RANGE;
        }
//        System.out.println("ILOŚĆ WYKONANIA PĘTLI: " + range);
        for(int i = 0; i < range; i++){
            blockStatementVisitor.visit(ctx.blockStatement());
        }

        return null;
    }
}
