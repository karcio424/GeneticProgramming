grammar GPproject;

// Reguły parsera
program: statement+;

statement:
	loopStatement
	| conditionalStatement
	| blockStatement
	| assignmentStatement
	| inputStatement
	| outputStatement;

//TUTAJ ZASTANOWIC SIE NAD expression? => blockStatement

loopStatement: 'loop' '(' expression ')' blockStatement;

conditionalStatement:	'if' '(' expression ')' blockStatement ('else' blockStatement)?;

blockStatement: '{' statement+ '}';

assignmentStatement: ID '=' expression';';

inputStatement: 'input' '(' program ')' ';';
//zmiana outputu na program i przypisanie do zmiennej
//inputTerm: INT | FLOAT | BOOL | STRING;

outputStatement: 'output' '(' ID (',' ID)* ')' ';';

expression: logicTerm (('&&' | '||') logicTerm)*;

logicTerm:	arithmeticExpression (('<' | '>' | '==' | '!=' | '<=' | '>=') arithmeticExpression )?;

arithmeticExpression: term (('+' | '-') term)*;

term: factor (('*' | '/' | '%') factor)*;

factor:
	ID
	| INT
	| FLOAT
	| BOOL
	| '(' expression ')'
	| '-' factor
	| '!' factor;

// Reguły leksykalne
INT: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]+;
BOOL: 'true' | 'false';
STRING: '"' ~["]* '"';
ID: [a-zA-Z_][a-zA-Z0-9_]*;
WS: [ \t\r\n]+ -> skip;