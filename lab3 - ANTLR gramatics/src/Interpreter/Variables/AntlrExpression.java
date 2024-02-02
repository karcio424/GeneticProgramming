package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrExpression extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitExpression(GPprojectParser.ExpressionContext ctx) {
        AntlrLogicTerm logicTermVisitor = new AntlrLogicTerm();
//        System.out.println("EXPRESSION: "+ctx.logicTerm(0).getText());
        Statement x = logicTermVisitor.visit(ctx.logicTerm(0));
//        System.out.println("EXPRESSION - OUT:"+((Factor) x).value);
//        int left = ((Factor) logicTermVisitor.visit(ctx.logicTerm(0))).value;

        if(ctx.logicTerm(1)!=null) {
//            System.out.println("TUUUUU");
            boolean left = ((BoolFactor) x).value;
            boolean right = ((BoolFactor) logicTermVisitor.visit(ctx.logicTerm(1))).value;

            boolean value;
            String character = ctx.getChild(1).getText();
            //TODO: whats wrong with && and ||
            switch (character) {
                case "&&" -> value = left && right;
                case "||" -> value = left || right;
                default -> throw new WrongProgramException("RuntimeException: Not known property\n");
            }
//            System.out.println(left + " " + right + " " + value);
            return new BoolFactor(value);
        }
//        System.out.println(ContextTable.variables);
//        System.out.println(x);
        return x;
    }
}
