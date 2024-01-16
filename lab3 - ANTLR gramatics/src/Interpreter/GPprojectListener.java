// Generated from ./GPproject.g4 by ANTLR 4.13.1
package Interpreter;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GPprojectParser}.
 */
public interface GPprojectListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GPprojectParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GPprojectParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(GPprojectParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(GPprojectParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(GPprojectParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(GPprojectParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#conditionalStatement}.
	 * @param ctx the parse tree
	 */
	void enterConditionalStatement(GPprojectParser.ConditionalStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#conditionalStatement}.
	 * @param ctx the parse tree
	 */
	void exitConditionalStatement(GPprojectParser.ConditionalStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(GPprojectParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(GPprojectParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(GPprojectParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(GPprojectParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#ioStatement}.
	 * @param ctx the parse tree
	 */
	void enterIoStatement(GPprojectParser.IoStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#ioStatement}.
	 * @param ctx the parse tree
	 */
	void exitIoStatement(GPprojectParser.IoStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#inputStatement}.
	 * @param ctx the parse tree
	 */
	void enterInputStatement(GPprojectParser.InputStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#inputStatement}.
	 * @param ctx the parse tree
	 */
	void exitInputStatement(GPprojectParser.InputStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#inputTerm}.
	 * @param ctx the parse tree
	 */
	void enterInputTerm(GPprojectParser.InputTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#inputTerm}.
	 * @param ctx the parse tree
	 */
	void exitInputTerm(GPprojectParser.InputTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#outputStatement}.
	 * @param ctx the parse tree
	 */
	void enterOutputStatement(GPprojectParser.OutputStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#outputStatement}.
	 * @param ctx the parse tree
	 */
	void exitOutputStatement(GPprojectParser.OutputStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(GPprojectParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(GPprojectParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#logicTerm}.
	 * @param ctx the parse tree
	 */
	void enterLogicTerm(GPprojectParser.LogicTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#logicTerm}.
	 * @param ctx the parse tree
	 */
	void exitLogicTerm(GPprojectParser.LogicTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#arithmeticExpression}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticExpression(GPprojectParser.ArithmeticExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#arithmeticExpression}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticExpression(GPprojectParser.ArithmeticExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(GPprojectParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(GPprojectParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPprojectParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(GPprojectParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPprojectParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(GPprojectParser.FactorContext ctx);
}