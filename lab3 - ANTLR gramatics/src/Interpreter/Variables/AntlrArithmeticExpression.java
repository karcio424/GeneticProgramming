package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrArithmeticExpression extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitArithmeticExpression(GPprojectParser.ArithmeticExpressionContext ctx) {
        AntlrTerm termVisitor = new AntlrTerm();

        Statement x = termVisitor.visit(ctx.term(0));

        if (ctx.term(1) != null) {

            int left = ((Factor) x).value;
            int right = ((Factor) termVisitor.visit(ctx.term(1))).value;
            String character = ctx.getChild(1).getText();
            return switch (character) {
                case "+" -> new Factor(left + right);
                case "-" -> new Factor(left - right);
                default -> null;
            };
        }
        return x;
    }
}
