package Interpreter.Extensions;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrAssignmentStatement extends GPprojectBaseVisitor<Command> {
    @Override
    public Command visitAssignmentStatement(GPprojectParser.AssignmentStatement ctx) {
        // AntlrExpression expressionVisitor = new AntlrExpression();
        // VariablesTable.addVariable(ctx.getChild(0).getText(), ((Variable) expressionVisitor.visit(ctx.getChild(2))).value);
        // return null;
    }
}
