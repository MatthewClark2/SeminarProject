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

tupleIdentifier : LPAREN ((IDENTIFIER | tupleIdentifier) (COMMA (IDENTIFIER | tupleIdentifier))*? COMMA?)? RPAREN ;
binding : (IDENTIFIER | tupleIdentifier) ;

assignment : binding ASSIGN expression ;

// Defined in descending order of precedence.
// TODO(matthew-c21) - Test to ensure that precendence is correctly followed.
expression : LPAREN expression RPAREN
           | NOT expression
           | left=expression op=POW right=expression
           | left=expression op=(MUL | DIV | MOD) right=expression
           | left=expression op=(PLUS | MINUS) right=expression
           | left=expression op=(LT | LE | GT | GE) right=expression
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
           | left=expression op=OR right=expression
           | left=expression op=AND right=expression
           | terminal=(CHAR | STRING | FLOAT | INT | BOOL | IDENTIFIER)
           ;

expressionList : (expression (COMMA expression)*? COMMA?)? ;

/*
 * Lexer Rules
 */

fragment IDENTIFIER_START : [a-zA-Z_] ;
fragment IDENTIFIER_PART : [a-zA-Z_0-9?] ;
fragment DIGIT : [0-9] ;
// TODO(matthew-c21) - Strings and comments can span multiple lines. Fix that.
fragment CONTENT : .*? ;

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

COMMENT : ( BLOCK_COMMENT_START CONTENT BLOCK_COMMENT_END | LINE_COMMENT_START CONTENT (NEWLINE | EOF) ) -> channel(HIDDEN) ;

// Primitive value literals.
fragment TRUE : 'True' ;
fragment FALSE : 'False' ;
BOOL : TRUE | FALSE ;

FLOAT  : DIGIT+ '.' DIGIT+ ;
INT : DIGIT+ ;

STRING_DELIMITER : '"' ;
STRING : STRING_DELIMITER CONTENT STRING_DELIMITER ;

CHAR_DELIMITER : '\'' ;
CHAR : CHAR_DELIMITER CONTENT CHAR_DELIMITER ;

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
