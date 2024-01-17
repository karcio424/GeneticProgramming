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
        Statement value = expressionVisitor.visit(ctx.getChild(2));
        if (value instanceof BoolFactor){
            ContextTable.addVariable(ctx.getChild(0).getText(), ((BoolFactor) value).value);
        }
        else{
            ContextTable.addVariable(ctx.getChild(0).getText(), ((Factor) value).value);
        }
        System.out.println("NEW-CONTEXT:"+ContextTable.variables);
         return null;
    }
}
