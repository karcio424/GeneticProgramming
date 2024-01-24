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
            System.out.println(left + " " + right + " " + value);
            return new BoolFactor(value);
        }
//        System.out.println(ContextTable.variables);
//        System.out.println(x);
        return x;
    }

//    @Override
//    public Statement visitVariableExpression(MiniGPLangParser.VariableExpressionContext ctx) {
//        AntlrVariable variableVisitor = new AntlrVariable();
//        return variableVisitor.visit(ctx.getChild(0));
//    }
//
//    @Override
//    public Statement visitInputExpression(MiniGPLangParser.InputExpressionContext ctx) {
//        AntlrInput inputVisitor = new AntlrInput();
//        return inputVisitor.visit(ctx.getChild(0));
//    }
//
//    @Override
//    public Statement visitMultiplicationDivision(MiniGPLangParser.MultiplicationDivisionContext ctx) {
//        AntlrExpression x = new AntlrExpression();
//
//        Variable l = (Variable) x.visit(ctx.getChild(0));
//        Variable r = (Variable) x.visit(ctx.getChild(2));
//
//        String character = ctx.getChild(1).getText();
//        if (Objects.equals(character, "*")){
//            return new Variable(l.value * r.value);
//        }
//        else if (Objects.equals(character, "/")){
//            if (r.value == 0)
//                throw new BadProgramException("RuntimeException: Division by Zero\n" +
//                        "The program encountered an attempt to divide by zero.");
//            return new Variable(l.value / r.value);
//        }
//        else return null;
//    }
}
