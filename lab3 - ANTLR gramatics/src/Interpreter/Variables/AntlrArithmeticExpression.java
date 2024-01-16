package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrArithmeticExpression extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitArithmeticExpression(GPprojectParser.ArithmeticExpressionContext ctx) {
        AntlrTerm termVisitor = new AntlrTerm();

        int left = ((Factor) termVisitor.visit(ctx.term(0))).value;

        //TODO: fix when right doesn't exist

        int right = ((Factor) termVisitor.visit(ctx.term(1))).value;

        String character = ctx.getChild(1).getText();
        return switch (character) {
            case "+" -> new Factor(left + right);
            case "-" -> new Factor(left - right);
            default -> null;
        };
        //        if (Objects.equals(character, "+")){
//            return new Factor(left+right);
//        }
//        else if (Objects.equals(character, "-")) {
//            return new Factor(left-right);
//        }
    }
}
