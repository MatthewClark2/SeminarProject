grammar Math;

@header {
package prj.clark.lang.math;
}

/*
 * Parser Rules
 */

// This definition is similar to the classical form of defining operations in reverse order.
expression : LPAREN expression RPAREN
           | expression EXP expression
           | expression MD expression
           | expression AS expression
           | NUMBER
           ;

statement : expression NEWLINE ;

/*
 * Grammar Rules
 */

fragment DIGIT : [0-9] ;
fragment NUMERIC_FRAGMENT : DIGIT+ ;

// Both the sign and value after the decimal are optional.
NUMBER : ( AS )? NUMERIC_FRAGMENT ('.' NUMERIC_FRAGMENT)? ;

// The use of compound operators is due to the fact that they have shared precedence.
LPAREN : '(' ;
RPAREN : ')' ;
EXP : '^' ;
MD : ( '*' | '/' );
AS : ( '+' | '-' );

NEWLINE : '\n' ;

WHITESPACE : [ \t] -> skip ;