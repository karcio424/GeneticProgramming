package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrExpression extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitExpression(GPprojectParser.ExpressionContext ctx) {
        AntlrLogicTerm logicTermVisitor = new AntlrLogicTerm();
        Statement x = logicTermVisitor.visit(ctx.logicTerm(0));

        if (ctx.logicTerm(1) != null) {
            boolean left = ((BoolFactor) x).value;
            boolean right = ((BoolFactor) logicTermVisitor.visit(ctx.logicTerm(1))).value;

            boolean value;
            String character = ctx.getChild(1).getText();
            switch (character) {
                case "&&" -> value = left && right;
                case "||" -> value = left || right;
                default -> throw new WrongProgramException("RuntimeException: Not known property\n");
            }
            return new BoolFactor(value);
        }
        return x;
    }
}
