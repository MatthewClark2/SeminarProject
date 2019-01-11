grammar Basic ;

// This grammar is used as a testbed for a simple grammar implementation.
@header {
package prj.clark.lang.basic;
}

statement : printStatement
          | declaration
          | assignment
          | expression
          ;

line : statement (NL | EOF) ;

file : lines=line+ ;

type : (INT | DEC | STR) ;

expression : LPAR expression RPAR
           | left=expression operator=POW right=expression
           | left=expression operator=(MUL | DIV | MOD) right=expression
           | left=expression operator=(ADD | SUB) right=expression
           | value=expression AS target=type
           | DECIMAL
           | INTEGER
           | STRING
           | IDENTIFIER
           ;

printStatement : PRINT expression ;
declaration : VAR assignment ;
assignment : IDENTIFIER EQ expression ;

// Keyword letters.
fragment P : ('p'|'P') ;
fragment R : ('r'|'R') ;
fragment I : ('i'|'I') ;
fragment N : ('n'|'N') ;
fragment T : ('t'|'T') ;
fragment V : ('v'|'V') ;
fragment A : ('a'|'A') ;
fragment S : ('s'|'S') ;

// Core language features.
PRINT : P R I N T ;
VAR : V A R ;
EQ : '=' ;
fragment QUOTE : '"' ;
AS : A S ;
INT : 'Int' ;
DEC : 'Float' ;
STR : 'String' ;

// Operators.
ADD  : '+' ;
SUB  : '-' ;
MUL  : '*' ;
DIV  : '/' ;
MOD  : '%' ;
POW  : '^' ;
LPAR : '(' ;
RPAR : ')' ;

// Data.
fragment DIGIT : [0-9] ;
DECIMAL : DIGIT+ '.' DIGIT+ ;
INTEGER : DIGIT+ ;
STRING : QUOTE .*? QUOTE ;

IDENTIFIER : [a-zA-Z_][a-zA-Z_0-9]* ;

NL : ('\n' | '\r\n' | '\r') ;
WS : [\t ] -> skip;