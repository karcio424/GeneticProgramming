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

            //TODO: fix when right doesn't exist

            //TODO: boolean val for x<y etc... (like Factor)
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
//        if (Objects.equals(character, "<")){
////            return new Factor(left && right);
////            boolean x = left || right;
//            //TEMPORARY VAL
//            return new Factor(left);
//        }
//        else if (Objects.equals(character, ">")) {
////            return new Factor(left==0 || right==0);
//            //TEMPORARY VAL
//            return new Factor(right);
//        }
//        else if (Objects.equals(character, "==")){
////            return new Factor(left==0 || right==0);
//            //TEMPORARY VAL
//            return new Factor(right);
//        }
//        else if (Objects.equals(character, "!=")){
////            return new Factor(left==0 || right==0);
//            //TEMPORARY VAL
//            return new Factor(right);
//        }
//        else if (Objects.equals(character, "<=")){
////            return new Factor(left==0 || right==0);
//            //TEMPORARY VAL
//            return new Factor(right);
//        }
//        else if (Objects.equals(character, ">=")){
////            return new Factor(left==0 || right==0);
//            //TEMPORARY VAL
//            return new Factor(right);
//        }
//        else return null;
//    }
//    @Override
//    public ComparisonOperator visitComparisonLess(MiniGPLangParser.ComparisonLessContext ctx) {
//        return ComparisonOperator.LESS;
//    }
//
//    @Override
//    public ComparisonOperator visitComparisonGreater(MiniGPLangParser.ComparisonGreaterContext ctx) {
//        return ComparisonOperator.GREATER;
//    }
//
//    @Override
//    public ComparisonOperator visitComparisonEqual(MiniGPLangParser.ComparisonEqualContext ctx) {
//        return ComparisonOperator.EQUAL;
//    }
//
//    @Override
//    public ComparisonOperator visitComparisonNotEqual(MiniGPLangParser.ComparisonNotEqualContext ctx) {
//        return ComparisonOperator.NOT_EQUAL;
//    }
