package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrArithmeticExpression extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitArithmeticExpression(GPprojectParser.ArithmeticExpressionContext ctx) {
        AntlrTerm termVisitor = new AntlrTerm();

//        System.out.println("TERM:"+ctx.term(0).getText());
        Statement x = termVisitor.visit(ctx.term(0));
//        System.out.println("TERM:"+((Factor) x).value);
//        System.out.println("TERM OUT:"+x + " " +ctx.term(1));

        //TODO: fix when right doesn't exist
        if(ctx.term(1)!=null) {
//            System.out.println("TERM:"+x);
            int left = ((Factor) x).value;
            int right = ((Factor) termVisitor.visit(ctx.term(1))).value;
//            System.out.println("TERM WAW "+left + " " + right);
            String character = ctx.getChild(1).getText();
//            System.out.println("TERM WAW "+left + " " + right + " " + character);
            return switch (character) {
                case "+" -> new Factor(left + right);
                case "-" -> new Factor(left - right);
                default -> null;
            };
        }
//        System.out.println("PRZED RETURNEM:"+((Factor) x).value);
        return x;
        //        if (Objects.equals(character, "+")){
//            return new Factor(left+right);
//        }
//        else if (Objects.equals(character, "-")) {
//            return new Factor(left-right);
//        }
    }
}
