grammar Alchemy ;

@header {
package prj.clark.lang.impl;
}

/*
 * Parser Rules
 */

statement : expression | assignment | COMMENT ;
statementBody : LBRACE line* RBRACE ;
line : statement STATEMENT_TERMINATOR ;
file : line* EOF ;

// The given regex allows for empty parentheses and trailing commas.
// This is a duplicate of tupleIdentifier since this definition will not be updated.
lambda : LPAREN (IDENTIFIER  (COMMA IDENTIFIER)*? COMMA?)? RPAREN statementBody ;

tuple : LPAREN (expression (COMMA expression)*? COMMA?)? RPAREN ;

// TODO(matthew-c21) - Determine how to best recursively define tupleIdentifiers.
tupleIdentifier : LPAREN (IDENTIFIER (COMMA IDENTIFIER)*? COMMA?)? RPAREN ;
binding : (IDENTIFIER | tupleIdentifier) ;

assignment : binding ASSIGN expression ;

// Defined in descending order of precedence.
expression : LPAREN expression RPAREN
           | NOT expression
           | left=expression op=POW right=expression
           | left=expression op=(MUL | DIV | MOD) right=expression
           | left=expression op=(PLUS | MINUS) right=expression
           | left=expression op=(LT | LE | GT | GE) right=expression
           | left=expression op=COLON right=expression
           | arg=expression op=(FEED_FIRST | FEED_LAST) func=expression
           | left=expression op=(EQ | NEQ) right=expression
           | func=expression args=tuple
           | left=expression IDENTIFIER right=expression
           | lambda
           | tuple
           | terminator=(CHAR | STRING | FLOAT | INT | BOOL | IDENTIFIER)
           ;

/*
 * Lexer Rules
 */

fragment IDENTIFIER_START : [a-zA-Z_] ;
fragment IDENTIFIER_PART : [a-zA-Z_0-9?] ;
fragment DIGIT : [0-9] ;

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

// Comments
BLOCK_COMMENT_START : '/*';
BLOCK_COMMENT_END : '*/';
LINE_COMMENT_START : '//' ;

COMMENT : ( BLOCK_COMMENT_START .*? BLOCK_COMMENT_END | LINE_COMMENT_START .*? (NEWLINE | EOF) ) -> channel(HIDDEN) ;

// Primitive value literals.
fragment TRUE : 'True' ;
fragment FALSE : 'False' ;
BOOL : TRUE | FALSE ;

FLOAT  : DIGIT+ '.' DIGIT+ ;
INT : DIGIT+ ;

STRING_DELIMITER : '"' ;
STRING : STRING_DELIMITER .*? STRING_DELIMITER ;

CHAR_DELIMITER : '\'' ;
CHAR : CHAR_DELIMITER .*? CHAR_DELIMITER ;

// Operators
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
NOT : '!' ;

// Assignment
ASSIGN : '=' ;

// Keywords
DEFN : 'defn' ;

// TODO(matthew-c21) - Newlines actually terminate a statement, but semicolons are easier to work with for the time being.
STATEMENT_TERMINATOR : ';' ;
NEWLINE : [\n\r] -> skip ;
WHITESPACE : [ \t] -> skip ;
IDENTIFIER : IDENTIFIER_START (IDENTIFIER_PART)* ;
