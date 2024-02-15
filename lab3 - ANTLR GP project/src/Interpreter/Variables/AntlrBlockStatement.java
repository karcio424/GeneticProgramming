package Interpreter.Variables;

import Interpreter.GPprojectBaseVisitor;
import Interpreter.GPprojectParser;
import org.antlr.v4.runtime.tree.TerminalNode;

public class AntlrBlockStatement extends GPprojectBaseVisitor<Statement> {
    @Override
    public Statement visitBlockStatement(GPprojectParser.BlockStatementContext ctx) {
        AntlrStatement statementVisitor = new AntlrStatement();
        for (int i = 1; i < ctx.getChildCount() - 1; i++)
            if (!(ctx.getChild(i) instanceof TerminalNode))
                statementVisitor.visit(ctx.getChild(i));

        return null;
    }
}
