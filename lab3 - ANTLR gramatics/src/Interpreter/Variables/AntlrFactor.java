package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrFactor extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitFactor(GPprojectParser.FactorContext ctx) {
        String varName = ctx.getChild(0).getText();
//        int left = ((Factor) factorVisitor.visit(ctx.factor(0))).value;
//
//        int right = ((Factor) factorVisitor.visit(ctx.factor(1))).value;

//        //TODO: add cases: ID, INT, FLOAT, ( expression )
        if (Objects.equals(varName, "true")) {
            //return TRUE;
            return new Factor(0);
        } else if (Objects.equals(varName, "false")) {
            //return FALSE;
            return new Factor(0);
        } else if (varName.matches("-?[0-9]+")) {
            // INT case
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
            return new Factor(0); // Placeholder value for ID, modify as needed
            //TODO: FIX this code
//        } else if (varName.startsWith("(") && varName.endsWith(")")) {
//            // ( expression ) case
//            String expression = varName.substring(1, varName.length() - 1).trim();
//            // Recursively evaluate the expression inside parentheses
//            Factor result = evaluateExpression(expression);
//            return result;
        } else {
            return null;
        }
    }
}
