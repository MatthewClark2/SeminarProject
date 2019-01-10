grammar Alchemy ;  // The language is currently nameless.

@header {
package prj.clark.lang.impl;
}

/*
 * Parser Rules
 */

// Components of a file and blocks.
statement : ((expression | assignment) SEMICOLON | comment);
statementBody : LBRACE statement* RBRACE ;
file : statement* ;

// Type literals.
bool : (TRUE | FALSE) ;
string : STRING_DELIMITER content=PRINTABLE_CHARACTER*? STRING_DELIMITER ;
char : CHAR_DELIMITER content=PRINTABLE_CHARACTER*? CHAR_DELIMITER ;
primitive : (STRING | FLOAT | INT | bool) ;

// TODO(matthew-c21) - Delete after testing.
prim : (char | string | FLOAT | INT | bool) NEWLINE ;
prims : prim* ;

// Function literal.
lambda : tupleIdentifier statementBody ;

tuple : LPAREN (expression (COMMA expression)*?)? RPAREN ;

// Comments. These are just discarded at runtime.
comment : COMMENT_START content=.*? COMMENT_END ;

// Binding semantics for destructuring.
tupleIdentifier : LPAREN (IDENTIFIER (COMMA IDENTIFIER)*?)? RPAREN ;
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
           | prefix=expression args=tuple
           | left=expression infix=expression right=expression
             // Terminals
           | lambda
           | primitive
           | conditional
           | IDENTIFIER
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

// TODO(matthew-c21) - Find a Unicode usable substitute.
PRINTABLE_CHARACTER : '\X' ;

// Offset values.
LPAREN : '(' ;
RPAREN : ')' ;
LBRACKET : '[' ;
RBRACKET : ']' ;
LBRACE : '{' ;
RBRACE : '}' ;
BAR : '|' ;
RANGE : '..' ;

COLON : ':' ;
COMMA : ',' ;
BLOCK_COMMENT_START : '/*';
BLOCK_COMMENT_END : '*/';

// TODO(matthew-c21) - Add string as a parser rule.
STRING_DELIMITER : '"' ;
CHAR_DELIMITER : '\'' ;

// Primitive value literals.
TRUE : 'True' ;
FALSE : 'False' ;
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
FEED_FIRST : '>>' ;
FEED_LAST : '<<' ;
ACCESS : '.' ;

ASSIGN : '=' ;

// Keywords
DEFN : 'defn' ;

NEWLINE : '\n' ;
WHITESPACE : [ \t\r] -> skip ;
IDENTIFIER : IDENTIFIER_START (IDENTIFIER_PART)* ;
