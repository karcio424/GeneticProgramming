package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrTerm extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitTerm(GPprojectParser.TermContext ctx) {
        AntlrFactor factorVisitor = new AntlrFactor();
        System.out.println("Factor:"+ctx.factor(0).getText());
        Statement x = factorVisitor.visit(ctx.factor(0));
//        System.out.println("Factor OUT:"+ ((Factor) x).value + " " +ctx.factor(1));

        if(ctx.factor(1)!=null) {
//            System.out.println("Factor:"+x);
            int left = ((Factor) x).value;
            //TODO: WARUNEK JESLI NIE MA righta!
            int right = ((Factor) factorVisitor.visit(ctx.factor(1))).value;
//            System.out.println("WAW "+left + " " + right);
            String character = ctx.getChild(1).getText();
//            System.out.println("WAW "+left + " " + right + " " + character);
            return switch (character) {
                case "*" -> new Factor(left * right);
                case "/" -> {
                    if (right == 0) {
                        throw new WrongProgramException("RuntimeException: Division by Zero\n" +
                                "The program encountered an attempt to divide by zero.");
                    }
                    yield new Factor(left / right);
                }
                case "%" -> new Factor(left % right);
                default -> null;
            };
        }
        return x;
    }
}
