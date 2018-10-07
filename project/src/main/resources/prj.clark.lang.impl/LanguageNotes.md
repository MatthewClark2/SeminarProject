# Language Notes
## Comments
This project is a remarkably large one, although it isn't quite as well
thought out as it probably ought to be. There are, however, some rules that I
have managed to force into place, and should be able to implement in a 
reasonably timely manner. 

Note that none of the examples include references to
types, which falsely gives the impression that types are either inferred or
dynamic. Neither of these are the case. I'm just not sure how to perform static
type checking, nor have I decided on how to work with generic types or the
syntax for type declaration in its entirety.

## Destructuring Semantics
One very important feature of all ergonomic languages is some sort of
destructuring operation. Languages like Clojure allow for very terse and
flexible operations, while languages like Python and Haskell focus on the more
common and practical applications of destructuring. This language is closer to
the latter on this subject, allowing for nested removal of elements, as well as
the collection or discarding of non-selected elements.

As an example, given some dictionary of Int-Int pairs, you could obtain the
first int of the second pair like so (excluding type specifiers, which are
mandatory after some work with type systems can be done):


    def (_,(x,_)) {1: 2, 3: 4}  // x = 3


## Assignment Semantics
As seen in the previous example, you just use an appropriate binding keyword, a
binding expression, and a value expression to assign one or more values. The
single underscore `_`, is special, as it is a dummy binding, and is never
assigned to at runtime.

### Binding Keywords
`def` creates a new constant value, which may not be reassigned to in the 
current context. The value may be freely shadowed outside of its definition 
though. For example:


    def x 5
    
    /* Some code */
    
    def x 5 * x  /* Illegal */
    
    defn f(y) {
      def x y ^ 2 + 5  /* Shadows the global x, so this is legal. */
 
 
`defmut` is very similar to `def`, although variables defined via `defmut` may
be freely reassigned at any point. Collections created with this identifier are
also subject to being modified. *Note, this specific clause may be subject to
change in the future.*

`defn` may be used to generate new functions, although it's really just
syntactic sugar for assigning a single identifier to a lambda. `defn` does not
have access to destruction of arguments with its binding clause. Example usage:


    defn printDouble(x) {
      print(2 * x)
    }
    
    // This is functionally equivalent to the above.
    def printDouble (x) { print(2 * x) }
    
    
In hindsight, due to the syntax for lambdas and assignment, this is a redundant
keyword, and will likely be removed in the future.

## Expressions
Everything barring classical loops, assignments, and user type definition is
considered as a value. Beyond normal values like identifiers, literals,
collections, and function invocations, you also have conditional statements as
expressions.

## Collections
The language will feature dictionaries, lists, and tuples as primary data
structures. Dictionaries are a type to type mapping, lists are of a homogeneous
type, and tuple types work like Haskell tuples.

### Iterators
Lazy evaluation is important in a language like this. Iterators greatly aid
that goal. All generated lists, be they from some function call or range 
generation, are not actually created until they are requested, with the
exception of the first element (or the realization that they are empty) upon
creation. Functions such as map and reduce simply affect elements returned by
these iterators.

## Function Usage Semantics
Under normal circumstances, functions are invoked via a C-like syntax. However,
there is support for infix notation, which works like you might expect. Example:


    /* Invoking a function via infix notation. */
    4 lshift 16  /* similar to 4 << 16 in C, as bitwise operators are 
                    not present. */
                    
    /* This is the same as*/
    lshift(4, 16)
    
    
Infix notation may only be used to apply two arguments. If a function only 
takes one  argument, it may not be used in infix notation. If it takes
more than two arguments, it will be curried.

Functions are automatically curried.

### Binary Operators
Binary operators are called as though they are infix functions. However, binary
operators are only language features. There are wrapper functions for them,
which may be useful when attempting to pass one as an argument, or for currying.
These stand-in functions exist within the language already, and are:

* add
* sub
* div
* mul
* pow
* mod 

These functions do not have the same rules of precedence that the binary
operators have.

## Notes on Extraneous Lexer Rules
If you glance in the Lang.g4 file, you may see a lot of unused rules. These are
there in case I decide to add certain features to the language in the future.
These may be safely ignored. Parser rules are the only worthwhile definitions
in the file.