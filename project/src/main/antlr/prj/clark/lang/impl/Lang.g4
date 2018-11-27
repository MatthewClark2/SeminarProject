grammar Lang ;  // The language is currently nameless.

@header {
package prj.clark.lang.impl;
}

/*
 * Parser Rules
 */

// Components of a file and blocks.
statement : ((expression | assignment | fnAssignment) SEMICOLON | comment);
statementBody : LBRACE statement* RBRACE ;
file : statement* ;

// Type literals.
bool : (TRUE | FALSE) ;
primitive : (STRING | FLOAT | INT | bool) ;

// Function literal.
lambda : tupleIdentifier statementBody ;

// Comments. These are just discarded at runtime.
comment : COMMENT_START content=.*? COMMENT_END ;

// Binding semantics for destructuring.
tupleIdentifier : LPAREN IDENTIFIER (COMMA IDENTIFIER)*? RPAREN ;
binding : (IDENTIFIER | tupleIdentifier) ;

// Assignment semantics.
fnAssignment : DEFN IDENTIFIER lambda ;
varAssignment : (DEF | DEFMUT) binding expression ;
assignment : (fnAssignment | varAssignment) ;

// Defined in descending order of precedence.
expression : LPAREN expression RPAREN
           | NOT expression
           | left=expression op=POW right=expression
           | left=expression op=(MUL | DIV | MOD) right=expression
           | left=expression op=(PLUS | MINUS) right=expression
           | left=expression op=(LT | LE | GT | GE) right=expression
           | left=expression op=(EQ | NEQ) right=expression
             // Function invocation.
           | left=expression infix=expression right=expression
           | prefix=expression LPAREN expressionList RPAREN
             // Terminals
           | IDENTIFIER
           | primitive
           | conditional
           | lambda
           ;

expressionList : expression (COMMA expression)* ;

// Flow control.
conditional : IF expression statementBody (ELIF expression statementBody)*? ELSE statementBody ;

/*
 * Lexer Rules
 */

fragment IDENTIFIER_START : [a-zA-Z_] ;
fragment IDENTIFIER_PART : [a-zA-Z_0-9?] ;
fragment UPPER : [A-Z] ;
fragment LOWER : [a-z] ;
fragment DIGIT : [0-9] ;

// Offset values.
LPAREN : '(' ;
RPAREN : ')' ;
LBRACKET : '[' ;
RBRACKET : ']' ;
LBRACE : '{' ;
RBRACE : '}' ;
BAR : '|' ;
ARROW : '<-' ;

COLON : ':' ;
SEMICOLON : ';' ;
COMMA : ',' ;
COMMENT_START : '/*';
COMMENT_END : '*/';


// Primitive value literals.
TRUE : 'true' ;
FALSE : 'false' ;

STRING : '"' .*? '"' ;

FLOAT  : DIGIT+ '.' DIGIT+ ;
INT : DIGIT+ ;

// Binops
PLUS : '+' ;
MINUS : '-' ;
DIV : '/' ;
MUL : '*' ;
POW : '^' ;
MOD : '%' ;
LT : '<' ;
GT : '>' ;
LE : '<=' ;
GE : '>=' ;
EQ : '==' ;
NEQ : '!=' ;
RANGE : '..' ;

// Even though the language is aimed at behaving primarily functionally, I'm keeping all of these here until they can be
// decisively removed at a later date.
ASSIGN : '=' ;
PLUS_ASSIGN : '+=' ;
MINUS_ASSIGN : '-=' ;
DIV_ASSIGN : '/=' ;
MUL_ASSIGN : '*=' ;
POW_ASSIGN : '^=' ;
MOD_ASSIGN : '%=' ;

// Keywords
OFTYPE : 'oftype' ;
IS : 'is' ;
AND : 'and' ;
OR : 'or' ;
NOT : 'not' ;
IF : 'if' ;
ELIF : 'elif' ;
ELSE : 'else' ;
FOR : 'for' ;
IN : 'in' ;
WHILE : 'while' ;
DEF : 'def' ;
DEFMUT : 'defmut' ;
DEFN : 'defn' ;
FN : 'fn' ;
DEFREC : 'defrec' ;
TYPE : 'type';
ENUM : 'enum' ;

WHITESPACE : [ \t\n\r] -> skip ;
IDENTIFIER : IDENTIFIER_START (IDENTIFIER_PART)* ;
