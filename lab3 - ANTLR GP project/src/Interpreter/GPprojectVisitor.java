// Generated from ./GPproject.g4 by ANTLR 4.13.1
package Interpreter;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GPprojectParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GPprojectVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GPprojectParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(GPprojectParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(GPprojectParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#conditionalStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalStatement(GPprojectParser.ConditionalStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(GPprojectParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(GPprojectParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#inputStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInputStatement(GPprojectParser.InputStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#outputStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutputStatement(GPprojectParser.OutputStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(GPprojectParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#logicTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicTerm(GPprojectParser.LogicTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#arithmeticExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticExpression(GPprojectParser.ArithmeticExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(GPprojectParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPprojectParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(GPprojectParser.FactorContext ctx);
}