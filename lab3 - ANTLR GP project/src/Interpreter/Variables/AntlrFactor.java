package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

import java.util.Objects;

public class AntlrFactor extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitFactor(GPprojectParser.FactorContext ctx) {
        String varName = ctx.getChild(0).getText();

        if (Objects.equals(varName, "true")) {
            return new BoolFactor(true);
        } else if (Objects.equals(varName, "false")) {
            return new BoolFactor(false);
        } else if (Objects.equals(varName, "input")) {
            if (AntlrProgram.currentIndex >= AntlrProgram.inputList.size()) {
                AntlrProgram.currentIndex = 0;
            }
            int value = AntlrProgram.inputList.get(AntlrProgram.currentIndex++);
            return new Factor(value);
        } else if (varName.matches("[0-9]+")) {
            return new Factor(Integer.parseInt(varName));
        } else if (varName.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            // ID case
            Object value = ContextTable.getVariableValue(varName);
            if (value == null) {
                return new Factor(0);
            }
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
            Statement value = expressionVisitor.visit(ctx.expression());

            if (value instanceof BoolFactor) {
                return new BoolFactor(((BoolFactor) value).value);
            }
            return new Factor(((Factor) value).value);
        } else {
            return null;
        }
    }
}
