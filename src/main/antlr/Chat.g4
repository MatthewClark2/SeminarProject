grammar Chat ;

@header {
package prj.clark.lang;
}

/*
 * Parser Rules
 */

chat                : line+ EOF ;

line                : name command message NEWLINE;

message             : (emoticon | link | color | mention | WORD | WHITESPACE)+ ;

name                : WORD WHITESPACE;

command             : (SAYS | SHOUTS) ':' WHITESPACE ;

emoticon            : ':' '-'? ')'
                    | ':' '-'? '('
                    ;

// Text has been modified to contain the brackets directly.
// link                : '[' TEXT ']' '(' TEXT ')' ;
link                : TEXT TEXT ;

color               : '/' WORD '/' message '/';

mention             : '@' WORD ;

/*
 * Lexer Rules
 */

fragment A          : ('A'|'a') ;
fragment S          : ('S'|'s') ;
fragment Y          : ('Y'|'y') ;
fragment H          : ('H'|'h') ;
fragment O          : ('O'|'o') ;
fragment U          : ('U'|'u') ;
fragment T          : ('T'|'t') ;

fragment LOWERCASE  : [a-z] ;
fragment UPPERCASE  : [A-Z] ;

SAYS                : S A Y S ;

SHOUTS              : S H O U T S;

WORD                : (LOWERCASE | UPPERCASE | '_')+ ;

WHITESPACE          : (' ' | '\t') ;

NEWLINE             : ('\r'? '\n' | '\r')+ ;

// We want to modify this, as it attempts to capture the largest available token, which is everything.
// TEXT                : ~[\]]+ ;
// This solution also kind of blows since it requires us to manually extract tokens, and fiddle with
// them ourselves.
// TEXT                : ('(' (.)+? ')'|'[' (.)+? ']') ; // ('['|'(') ~[])]+ (']'|')') ;
// We replace it with a semantic predicate. Sadly, this part is language specific.
TEXT                : {_input.LA(-1) == '[' || _input.LA(-1) == '('}? ~[])]+ ;