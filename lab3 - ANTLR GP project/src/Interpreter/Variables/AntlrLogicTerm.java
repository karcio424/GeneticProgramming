package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrLogicTerm extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitLogicTerm(GPprojectParser.LogicTermContext ctx) {
        AntlrArithmeticExpression arithmeticExpressionVisitor = new AntlrArithmeticExpression();
        Statement x = arithmeticExpressionVisitor.visit(ctx.arithmeticExpression(0));

        if (ctx.arithmeticExpression(1) != null) {
            int left = ((Factor) x).value;
            int right = ((Factor) arithmeticExpressionVisitor.visit(ctx.arithmeticExpression(1))).value;

            boolean value;
            String character = ctx.getChild(1).getText();
            switch (character) {
                case "<" -> value = left < right;
                case ">" -> value = left > right;
                case "==" -> value = left == right;
                case "!=" -> value = left != right;
                case "<=" -> value = left <= right;
                case ">=" -> value = left >= right;
                default -> throw new WrongProgramException("RuntimeException: Not known property\n");
            }
            return new BoolFactor(value);
        }
        return x;
    }
}
