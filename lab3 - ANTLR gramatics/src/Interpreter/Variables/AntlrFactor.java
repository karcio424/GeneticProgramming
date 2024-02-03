package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrFactor extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitFactor(GPprojectParser.FactorContext ctx) {
        String varName = ctx.getChild(0).getText();
//        System.out.println(varName);

        if (Objects.equals(varName, "true")) {
//            System.out.println("TRUE-CASE");
            return new BoolFactor(true);
        } else if (Objects.equals(varName, "false")) {
//            System.out.println("FALSE-CASE");
            return new BoolFactor(false);
        } else if (Objects.equals(varName, "input")) {
//            System.out.println("INPUT HANDLER");
            if (AntlrProgram.currentIndex >= AntlrProgram.inputList.size()) {
                AntlrProgram.currentIndex = 0;
            }
            int value = AntlrProgram.inputList.get(AntlrProgram.currentIndex++);
            return new Factor(value);
        }else if (varName.matches("[0-9]+")) {
//            System.out.println("INT-CASE " + varName);
            return new Factor(Integer.parseInt(varName));
        } else if (varName.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            // ID case
//            System.out.println("ID-CASE");
            Object value = ContextTable.getVariableValue(varName);
            if (value instanceof Boolean) {
                return new BoolFactor((Boolean) value);
            }
            return new Factor((Integer) value);
        } else if (varName.startsWith("-")) {
            String varValue = ctx.getChild(1).getText();
            return new Factor(-Integer.parseInt(varValue));
        } else if (varName.startsWith("!")) {
            String varValue = ctx.getChild(1).getText();
            return new BoolFactor(!Boolean.parseBoolean(varValue));
        } else if (varName.startsWith("(")) {
            AntlrExpression expressionVisitor = new AntlrExpression();
//            System.out.println("( ) CASE" + ctx.expression().getText());
            Statement value = expressionVisitor.visit(ctx.expression());

            // ( expression ) case
//            String expression = varName.substring(1, varName.length() - 1).trim();
//            // Recursively evaluate the expression inside parentheses
//            Factor result = evaluateExpression(expression);
//            return result;
            if(value instanceof  BoolFactor) {
//                System.out.println("WYNIK NAWIASOW " + ((BoolFactor) value).value);
                return new BoolFactor(((BoolFactor) value).value);
            }
//            System.out.println("WYNIK NAWIASOW " + ((Factor) value).value);
            return new Factor(((Factor)value).value);
        } else {
            return null;
        }
    }
}
