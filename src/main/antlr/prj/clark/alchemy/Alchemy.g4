grammar Alchemy ;

@header {
package prj.clark.alchemy;
}

/*
 * Parser Rules
 */

statement : functionDeclaration | expression | assignment | COMMENT ;
statementBody : LBRACE statement* RBRACE ;
file : statement* EOF ;

// The given regex allows for empty parentheses and trailing commas.
// This is a duplicate of tupleIdentifier since this definition will not be updated.
lambda : LPAREN (IDENTIFIER  (COMMA IDENTIFIER)*? COMMA?)? RPAREN statementBody withBlock? ;

functionDeclaration : DEFN IDENTIFIER lambda ;

withBlock : (WITH tupleIdentifier AS tuple | WITH IDENTIFIER AS expression) ;

tuple : LPAREN expressionList RPAREN ;
list : LBRACKET expressionList RBRACKET ;
slice : LBRACKET (start=expression? COLON)? (end=expression? COLON)? skip=expression RBRACKET;
range : LBRACKET (start=expression (COMMA second=expression)?)? RANGE (end=expression)? RBRACKET ;
dict : LBRACE (expression COLON expression (COMMA expression COLON expression)*? COMMA?)? RBRACE ;

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
           | left=expression TICK infix=expression TICK right=expression
           | lambda
           | tuple
           | list
           | expression slice
           | range
           | dict
           | cond=expression QUESTION true=expression COLON false=expression
           | expression OR expression
           | expression AND expression
           | terminal=(CHAR | STRING | FLOAT | INT | BOOL | IDENTIFIER)
           ;

expressionList : (expression (COMMA expression)*? COMMA?)? ;

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
QUESTION : '?' ;
TICK : '`' ;

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
OR : 'or' ;
AND : 'and' ;

// Assignment
ASSIGN : '=' ;

// Keywords
DEFN : 'defn' ;
IMPORT : 'import' ;
WITH : 'with' ;
AS : 'as' ;
IMPORTALL : 'importall' ;

NEWLINE : [\n\r] -> skip ;
WHITESPACE : [ \t] -> skip ;
IDENTIFIER : IDENTIFIER_START (IDENTIFIER_PART)* ;
