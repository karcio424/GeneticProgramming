package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrLogicTerm extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitLogicTerm(GPprojectParser.LogicTermContext ctx) {
        AntlrArithmeticExpression arithmeticExpressionVisitor = new AntlrArithmeticExpression();
        System.out.println("LOGIC: "+ctx.arithmeticExpression(0).getText());
        Statement x = arithmeticExpressionVisitor.visit(ctx.arithmeticExpression(0));
//        System.out.println("LOGIC - OUT: "+((Factor) x).value);

        if(ctx.arithmeticExpression(1)!=null) {
            int left = ((Factor) x).value;
            int right = ((Factor) arithmeticExpressionVisitor.visit(ctx.arithmeticExpression(1))).value;

            boolean value;
            String character = ctx.getChild(1).getText();
//            System.out.println("WAW "+left + " " + right + " " + character);
            switch (character) {
                case "<" -> value = left < right;
                case ">" -> value = left > right;
                case "==" -> value = left == right;
                case "!=" -> value = left != right;
                case "<=" -> value = left <= right;
                case ">=" -> value = left >= right;
                default -> throw new WrongProgramException("RuntimeException: Not known property\n");
            }
            System.out.println(left + " " + right + " " + value);
            return new BoolFactor(value);
        }
        //TODO: change to value
        return x;
    }
}
