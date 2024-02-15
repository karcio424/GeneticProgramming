package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;


public class AntlrAssignmentStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitAssignmentStatement(GPprojectParser.AssignmentStatementContext ctx) {
        AntlrExpression expressionVisitor = new AntlrExpression();
        if (ctx.expression() != null) {
            Statement value = expressionVisitor.visit(ctx.expression());
            String variableName = ctx.ID().getText();
            if (value instanceof BoolFactor) {
                ContextTable.addVariable(variableName, ((BoolFactor) value).value);
            } else {
                ContextTable.addVariable(variableName, ((Factor) value).value);
            }
        }
        return null;
    }
}
