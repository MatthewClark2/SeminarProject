// We can't use a normal (combined) grammar due to the use of *lexical modes*.
// Modes are often used in string interpolation, or context specific grammars.
lexer grammar MarkupLexer ;

@header {
package prj.clark.lang;
}

OPEN             : '[' -> pushMode(BBCODE) ;
TEXT             : ~('[')+ ;

// Parsing content inside of tags.
mode BBCODE ;

CLOSE            : ']' -> popMode ;
SLASH            : '/' ;
EQUALS           : '=' ;
STRING           : '"' .*? '"' ;
ID               : LETTERS+ ;
WS               : [\t\n\r] -> skip ;

fragment LETTERS : [a-zA-Z] ;