grammar Lang ;  // The language is currently nameless.

@header {
package prj.clark.lang.impl;
}

/*
 * Parser Rules
 */

// Components of a file and blocks.
statement : (expression | assignment | fnAssignment) SEMICOLON ;
statementBody : LBRACE statement* RBRACE ;
file : statement* ;

// Type literals.
bool : (TRUE | FALSE) ;
primitive : (STRING | FLOAT | INT | bool) ;

// Collection literals.
tuple : LPAREN (expressionList)? RPAREN ;
array : LBRACKET (expressionList)? RBRACKET ;
dictEntry : expression COLON expression ;
dictEntryList : dictEntry (COMMA dictEntry)* ;
dict : LBRACE dictEntryList? RBRACE ;

// Function literal.
lambda : tupleIdentifier statementBody ;

// Comments. These are just discarded at runtime.
comment : COMMENT_START content=.*? COMMENT_END ;

// Binding semantics for destructuring.
tupleIdentifier : LPAREN (tupleIdentifier | IDENTIFIER) (COMMA (tupleIdentifier | IDENTIFIER))* RPAREN;
// The :IDENTIFIER is for type specification. This is in place for later.
// It is left as optional now due to the implementation being ignored in the
// early stages of the language.
binding : (IDENTIFIER | tupleIdentifier) (COLON IDENTIFIER)? ;

// Assignment semantics.
fnAssignment : DEFN IDENTIFIER lambda ;
assignment : (DEF | DEFMUT) binding expression ;

// Defined in descending order of precedence.
expression : LPAREN expression RPAREN
           | NOT expression
           | left=expression op=POW right=expression
           | left=expression op=(MUL | DIV | MOD) right=expression
           | left=expression op=(PLUS | MINUS) right=expression
           | left=expression op=(LT | LE | GT | GE) right=expression
           | left=expression op=(EQ | NEQ) right=expression
           | prefix
           // Infix notation
           | left=expression func=IDENTIFIER right=expression
           | IDENTIFIER
           | primitive
           | array
           | tuple
           | dict
           | conditional
           | lambda
           ;

// Function invocation.
expressionList : expression (COMMA expression)* ;
prefix : func=IDENTIFIER args=tuple ;

// Flow control.
conditional : IF expression statementBody (ELIF expression statementBody)*? ELSE statementBody ;
whileLoop : WHILE expression statementBody ;
forLoop : FOR binding IN expression statementBody ;

// Add list comprehensions similar to Haskell.

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

IDENTIFIER : IDENTIFIER_START (IDENTIFIER_PART)* ;

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
