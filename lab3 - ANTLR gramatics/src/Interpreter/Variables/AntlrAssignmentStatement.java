package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;

public class AntlrAssignmentStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitAssignmentStatement(GPprojectParser.AssignmentStatementContext ctx) {
         AntlrExpression expressionVisitor = new AntlrExpression();
        System.out.println("CONTEXT:"+ContextTable.variables+" "+
                ctx.getChild(0).getText()+" "+
                ctx.getChild(2).getText());
         ContextTable.addVariable(ctx.getChild(0).getText(), ((Factor) expressionVisitor.visit(ctx.getChild(2))).value);
         return null;
    }
}
