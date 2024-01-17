package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrTerm extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitTerm(GPprojectParser.TermContext ctx) {
        AntlrFactor factorVisitor = new AntlrFactor();
//        System.out.println("Factor:"+ctx.factor(0) + " " + ctx.factor(1));
        Statement x = factorVisitor.visit(ctx.factor(0));
//        System.out.println("Factor:"+x);

        if(ctx.factor(1)!=null) {
//            System.out.println("Factor:"+x);
            int left = ((Factor) factorVisitor.visit(ctx.factor(0))).value;
            //TODO: WARUNEK JESLI NIE MA righta!
            int right = ((Factor) factorVisitor.visit(ctx.factor(1))).value;

            String character = ctx.getChild(1).getText();
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
//        if (Objects.equals(character, "*")){
//            return new Factor(left*right);
//        }
//        else if (Objects.equals(character, "/")) {
//            if (right == 0)
//                throw new WrongProgramException("RuntimeException: Division by Zero\n" +
//                        "The program encountered an attempt to divide by zero.");
//            return new Factor(left/right);
//        }
//        else if (Objects.equals(character, "%")) {
//            return new Factor(left%right);
//        }
//        else return null;
    }
}
