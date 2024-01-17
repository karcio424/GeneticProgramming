package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrFactor extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitFactor(GPprojectParser.FactorContext ctx) {
        String varName = ctx.getChild(0).getText();
//        System.out.println("VAR_NAME "+varName);
//        int left = ((Factor) factorVisitor.visit(ctx.factor(0))).value;
//
//        int right = ((Factor) factorVisitor.visit(ctx.factor(1))).value;

//        //TODO: add cases: ID, INT, FLOAT, ( expression )
        if (Objects.equals(varName, "true")) {
            //return TRUE;
            System.out.println("TRUE-CASE");

            return new BoolFactor(true);
        } else if (Objects.equals(varName, "false")) {
            //return FALSE;
            System.out.println("FALSE-CASE");

            return new BoolFactor(false);
        } else if (varName.matches("-?[0-9]+")) {
            // INT case
            System.out.println("INT-CASE " + varName);
            return new Factor(Integer.parseInt(varName));
            //TODO: FIX this code
//        } else if (varName.matches("-?[0-9]+\\.[0-9]+")) {
//            // FLOAT case
//            return new Factor(Double.parseDouble(varName));
//        } else if (varName.matches("\".*\"")) {
//            // STRING case
//            // Extract the string content without quotes
//            String stringValue = varName.substring(1, varName.length() - 1);
//            return new Factor(stringValue);
        } else if (varName.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            // ID case
            // Handle ID logic here, maybe look up the value in a symbol table
            System.out.println("ID-CASE");
            Object value = ContextTable.getVariableValue(varName);
            if(value instanceof  Boolean) {
                return new BoolFactor((Boolean) value);
            }
            return new Factor((Integer) value);
            //TODO: FIX this code
        } else if (varName.startsWith("(")) {
            AntlrExpression expressionVisitor = new AntlrExpression();
            System.out.println("( ) CASE" + ctx.expression().getText());
            Statement value = expressionVisitor.visit(ctx.expression());

            // ( expression ) case
//            String expression = varName.substring(1, varName.length() - 1).trim();
//            // Recursively evaluate the expression inside parentheses
//            Factor result = evaluateExpression(expression);
//            return result;
            if(value instanceof  BoolFactor) {
                System.out.println("WYNIK NAWIASOW " + ((BoolFactor) value).value);
                return new BoolFactor(((BoolFactor) value).value);
            }
            System.out.println("WYNIK NAWIASOW " + ((Factor) value).value);
            return new Factor(((Factor)value).value);
        } else {
            return null;
        }
    }
}
