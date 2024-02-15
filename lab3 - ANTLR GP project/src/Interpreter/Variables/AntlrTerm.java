package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrTerm extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitTerm(GPprojectParser.TermContext ctx) {
        AntlrFactor factorVisitor = new AntlrFactor();
        Statement x = factorVisitor.visit(ctx.factor(0));

        if (ctx.factor(1) != null) {
            int left = ((Factor) x).value;
            int right = ((Factor) factorVisitor.visit(ctx.factor(1))).value;
            String character = ctx.getChild(1).getText();
            return switch (character) {
                case "*" -> new Factor(left * right);
                case "/" -> {
                    if (right == 0) {
                        yield new Factor(left);
                    }
                    yield new Factor(left / right);
                }
                case "%" -> {
                    if (right <= 0) {
                        yield new Factor(left);
                    }
                    yield new Factor(left % right);
                }
                default -> null;
            };
        }
        return x;
    }
}
