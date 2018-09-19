parser grammar MarkupParser ;

@header {
package prj.clark.lang;
}

// This is one of many available options for configuration. We use this since the grammar is split.
options { tokenVocab = MarkupLexer; }

file : element* ;
attribute : ID '=' STRING ;
content : TEXT ;
element : (content | tag) ;

// Note that all of these string literals could be replaced with the literal characters that they represent, but ANTLR
// does this automatically for pre-defined tokens.
tag : '[' ID attribute? ']' element* '[' '/' ID ']' ;