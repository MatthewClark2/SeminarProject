# Alchemy (name not final) Language Specification
## Types
All operations are considered to be strongly typed according to the attributes of a given piece of data. Attributes include:

* `Numeric` for data that may be represented as an integer or floating point number.
* `Sequenced` for data that may be iterated over in a determinant, linear order. All sequenced data is inherently iterable.
* `HoldsAttribute` for data that contains other data.
* `Indexed` for data that may be indexed directly.
* `Sliceable` for data that may use slice notation for indexing.
* `Printable` for data that can be displayed in a string format.
* `Invokable` for function-like data.

If the data provided to an operation does not conform to the correct types, an exception will be raised during runtime.

## Core syntax
### Operators
All operators are indifferent with regards to the whitespace between themselves and their corresponding operand. All of the following statements mean the same thing:

    1 + 2
    
    1+2
    
    1 +
    2
    
    1
    + 2

#### Binary Numeric Operators
All of these operators require that both operands are `Numeric`. If either operand does not match the appropriate type, a `TypeMismatchException` will be thrown.

* Add `+` - Add two values together, returning a new `Numeric` value. 
* Sub `-` - Subtract the second operand from the first, returning a new `Numeric`.
* Mul `*` - Multiply two values together, returning a `Numeric`. 
* Div `/` - Divide the first value by the second, returning a `Numeric`.
* Exp `^` - Raise the first argument to the power of the second, returning a `Numeric`.
* Mod `%` - Perform modular division between the two operands, returning a `Numeric`.

#### Binary Logical Operators
These operators always return booleans directly, and do not have inherent failure conditions.

* `or` - logical or
* `and` - logical and

#### Binary Relational Operators
Note that none of the relational operators perform any sort of type coercion.

* Eq `==` - determine if two values are equal to one another, returning a `Boolean`.
* Lt `<` - determine if the second value is less than the first, returning a `Boolean`. 
* Lte `<=` - determine if the second value is less than or equal to the first, returning a `Boolean`.
* Gt `>` - determine if the second value is greater than the first, returning a `Boolean`.
* Gte `>=` - to determine if the second value is greater than or equal to the first, returning a `Boolean`.
* Neq `!=` - Determine if two operands are not equal, returning a `Boolean`.

Note that the `<`, `<=`, `>`, and `>=` require two `Numeric` operands, and will throw a `TypeMismatchException` if a non-numeric operand is provided.

#### Special Binary Operators
* Feed-first `>>` - Takes the result of the first operand, and passes it as the first argument to the second operand. If the second operand is not a function, this operation throws a `TypeMismatchException`.
* Feed-last `<<` - Takes the result of the first operand, and passes it as the final argument to the second operand. If the second operand is not a function, this operation throws a `TypeMismatchException`.
* Access `.` - Attempts to obtain an attribute from a module. If the first operand is not a module or if the second operand isn't an identifier, this structure will result in a syntax error. 
* Cat `:` - Takes a sequence and some element. A new sequence containing the given element at the start or end of the stream will be returned. If both arguments are sequences, the second sequence will be added as an element at the end of the first sequence.
   * `element : sequence` - element is prepended
   * `sequence : element` - element is appended

#### Unary Operators
* Not `!` - Coerces a value into a boolean, then negates it.

Due to infix notation and the presence of the subtraction operator, numeric negation is done through a function rather than through a dedicated unary operator.

#### Other Operators
* Apply `()` - attempts to apply the function preceding the parentheses with the values inside the parentheses. If the preceding value is not a function, then a `TypeMismatchException` will be thrown.
* Index `[]` - takes between two and four arguments. The preceding value must be `Indexed`, and all values inside the brackets must resolve to integers. If either of these conditions is not met, then a `TypeMismatchException` will be thrown. For more information about how the actual indexing works, see [below](#Lists).
* Ternary `condition ? value if true : value if false` - basic conditional code execution. The initial expression is reduced to a boolean, and then one of the two cases will be executed and returned. Note that the unevaluated portion of code will not be executed at all.

An example of a ternary expression would be `true ? 5 : 0`. The colon in a ternary has a higher precedence that a colon in the embedded expressions. For example, `False ? 1:[2, 3] : 4` returns `[2, 3, 4]`, not `4`.

### Binding
Binding a value to an identifier is remarkably straightforward: just use a single equals sign. Due to the dynamic duck-typing system, variables declaration and definition is done in a single step.

    identifier = value
    
Note that bindings aren't constant within the scope in which they were created. You can rebind a local variable as many times as you wish, but modules and attributes within imported modules may not be modified.

    a = 2
    a = 3             // Legal!
    
    import module
    module.foo = 8    // Illegal!
    module = "hello"  // Illegal!

Functions defined using the `defn` keyword are also immutable. However, lambdas assigned to a variable are mutably bound.

#### Identifiers
Identifiers may begin with any alphabetic character, or an underscore. The body of an identifier may be composed of any alphanumeric character, an underscore, or a mix thereof. All other characters are considered to be illegal.

#### Multiple Binding
For functions that return multiple values, a tuple of bindings (which can even be nested) may be used instead.

    (x, y) = MaxAndMin(1, 2, 3)  // Both x and y have values.
    
    ((a, b), (c, d), (e, f), (g, h)) = Rect(2, 3) // All of a, b, c, d, e, f, g, and h have values.

### Comments
Comments can either be written over a region or to the end of the line. 

    // This is a single line comment.
    
    /* This is a multi-line comment. */
    
Comments are discarded at parse time, and they can be included in the middle of other expressions for documentation or explanatory purposes.

## Data
### Tuples
Tuples are the primary means of returning multiple values out of a function, and are otherwise identical to standard lists. A tuple may be created like so with any number of elements:

    (value, value, value ...)
    
Tuples are the only way to utilize tuple binding. 

#### Type Compliance
`Sequenced, Indexed, Printable`

### Lists
Lists are the primary sequence type, representing a collection of ordered data. The standard syntax for creating a list literal is:

    [1, 2, 3]

Oftentimes, you'll find your lists being produced lazily by other functions rather than produced literally.

#### Access
Barring the standard set of functions for dealing with and manipulating lists, you have standard Python slice syntax for eagerly reading part of a list into a sublist. There's also the basic zero-indexed index access available in most languages.

    nums[1] // Yield the second number.
    
    nums[1:3] // Yield the second and third numbers as a smaller list.
    
The slice syntax comes down to three arguments, all optional, delimited by colons.
* The first dictates the starting index. If the second and third arguments are omitted, then this is taken to represent a single element.
* The second dictates the ending index, non-inclusive. This value should be greater than the first argument unless you want an empty slice.
* The third dictates the interval between selected elements. If this value is non-positive, then an error will be thrown.

#### Ranges
Ranges are an easy way to define a series of integers starting from one value and changing by a constant value before reaching a terminating value. All three values are optional, and have defaults. The basic syntax is:

    [first, second .. end]
    
The first and second values are the literal first and second values of the list. The second value minus the first is the interval for the range. The default value for the first argument is 0. The default value for the second argument is whatever the first argument is plus 1. The default final argument is positive infinity. The common use of range literals is to create infinite series to serve as the basis of more complicated iterative sequences.

#### Type Compliance
`Sequenced, Indexed, Sliceable, Printable`

### Numeric Types
Numbers are, by default, either 64 bit signed integers or 64 bit floating point decimals. Numeric literals may be written:

    123
    3.14
    
Integers may often be coerced into floating point numbers during calculation, so keep that in mind while managing your math.

#### Type Compliance
`Numeric, Printable`

### Booleans
Booleans are fairly standard true/false values like you might find in any language. Booleans are special since they are the only data type that other types can be automatically coerced into. For collections, they are considered true if non-empty. For numbers, they are considered true if non-zero. The two boolean literals are:

    True
    False

#### Type Compliance
`Printable`

### Strings and characters
Strings are relatively weakly enforced right now, but effectively behave as eager lists of characters. Individual characters are more or less just Unicode code points, and are literally given in the form `'...'` where the internal ellipsis is to be replaced by something that may be parsed as a single character. 

Characters themselves aren't particularly useful on their own, which is why most string utilities overwrite the need for them. Strings are any text delimited by double quotes, with some valid escape options available. Examples of literals include:

    "Hello, world"
    
    "\u1F914" // Thinking face as a string
    
    '\u1f914' // Thinking face as a character
    
    'c' // The literal character 'c'.
    
Escape sequences available in strings and characters include:
* The aforementioned unicode escape for creating unicode characters
* Standard whitespace escapes (`\t, \n, \r, \f`)
* Backslash `\\`
* Double quotation mark `\"`
* Single quotation mark `\'`
* Backspace `\b`

Since strings are just lists of characters, you can make a string equivalent like so:

    ['h', 'e', 'l', 'l', 'o']
    
This list will not print as a normal string, but any produced iterators will be identical.

#### Type Compliance
Strings - `Sequenced, Indexed, Sliceable, Printable`

Characters - `Printable`

### Dictionaries
Dictionaries are basic associative data structures. Dictionaries are given via the standard dictionary literal syntax:

    { key : value, key : value, ... }
    
Note that dictionaries are fully formed at their time of creation as opposed to lists.

#### Type Compliance
`Sequenced, Indexed, Printable`

### Functions
#### Lambdas
Function literals are a nice way to get a small bit of recyclable code for use within a localized context. They automatically capture the surrounding variables due to everything in the language being treated as immutable (within reason). Any created variables only exist within the local scope, and are allowed to shadow variables from the original scope, without any effect on the original binding. The syntax for a lambda is pretty straightforward, similar to languages like Javascript.

    (a, b) {a ^ b}  // Single expression lambda that takes two arguments, raising the first argument to the power of the second.
    
    () { 5 } // Zero argument lambda that yields 5.
    
    (x) {
      y = f(x)
      z = g(x)
      y ^ z / y
    } // Multi-line lambda expression.
    
Named functions are mostly just syntactic sugar for assigning a lambda to a regular binding.

    defn f(x) { ... } // is the same as
    f = (x) { ... }

Function application can either be done with the traditional parenthetical notation, or with a specialized infix notation.

    f(1, 2)  // is the same as
    1 `f` 2
    
If a function receives too many arguments, then an exception is thrown. If a function does not receive enough arguments to be applied fully, then it will be partially applied, and the partially applied function will be returned as a result.

#### The 'with' Block
After the body of a function, you can add an optional `with` block, which is just a way to cache values into their own variables. The third lambda from earlier can be re-written as

    (x) { y ^ z / y } with (y, z) as (f(x), (g(x)))
    
Both forms behave exactly the same, although you can't control the timing or order of the calculation of y and z in the second example. As a result, `y` and `z` may not be defined in terms of one another when using a `with` block.

#### Type Compliance
`Invokable`

### Modules
Modules serve as the primary namespace system in Alchemy. While you can't manually set your namespaces like you can in languages like C++, you do have the luxury of automatically generated namespaces according to the natural file hierarchy of your project without anything too heavy for quick script use.

Each individual file and folder within a project is automatically registered as part of the language's built-in module system. However, in order for a file or folder to be recognized as valid module candidates, their filenames must adhere to standard identifier guidelines. Individual files must also end with the correct file extension. Suppose that you have the following file hierarchy, where `main.ext` refers to the launched program:

    src
    |-- bar
    |   `-- barlib1.ext
    |   `-- barlib2.ext
    `-- foo.ext
    `-- main.ext

After importing `bar` and `foo` as modules, you can access various attributes within those modules. In this case, you can directly access attributes within `foo`, while usable attributes within `bar` are hidden beneath a few submodules. Access is straightforward enough through the use of the access operator, like so:

    // Within main.ext after importing foo and bar.
    foo.f(1, 2)
    bar.barlib1.g("hello, world")

#### Type Compliance
`HoldsAttribute`

## Structure of a Program
A single file will consist of a series of definitions or expressions. When executed either for the sake of module generation or program execution, only the definitions will have any effect. Due to the implicit contract that functions should be referentially transparent, all top-level standalone expressions will be discarded before use. This includes non-referentially transparent functions and IO functions. In order to ensure that your commands are used, place executable code into a zero parameter function named `main`. When a module is executed, the interpreter will search for a `main` function, and attempt to invoke it. For example, the following bit of code can be executed directly.

    a = 7
    b = inc(a)
    
    defn main() {
      print(a + b)
    }
    
This can not be executed, and may not have an effect when loaded other than defining `a` and `b`.

    a = 7
    b = inc(a)
    
    print(a + b)  // This line may be freely discarded by the interpreter

## Imports
You have the ability to import an entire file or folder as a single module. When importing a file, all attributes that do not begin with an underscore are exported into the resulting module automatically. Attributes that do start with an underscore are considered hidden. However, should you need access to hidden attributes for unit testing or debugging, the language provides a special `importall` command, which generates a module containing all attributes. Assume that you have a file hierarchy like so:

    src
    |-- bar
    |   `-- barlib1.ext
    |   `-- barlib2.ext
    `-- foo.ext
    `-- main.ext

You have the ability to import files individually, or as a group. Files may even be aliased upon import.

    import foo          // Generates a module foo from which all members defined in foo.ext can be accessed.
    
    import bar          // Generates a module bar containing barlib1 and barlib2 as submodules.
    
    import bar.barlib1  // Generates a module barlib1 ignoring any attributes in bar or barlib2.
    
    import foo as f     // Identical to the first import, but aliases the foo module as simply f.
    
You may import any attribute from a module, although non-module attributes may also be imported directly. As an example, assume that the `barlib2.ext` from earlier has attributes `helpera` and `helperb`.

    import bar.barlib1                // Legal!

    import bar.barlib2                // Legal!
    barlib2.helperfn                  // Legal!

    // Individual definitions can be imported directly.
    import bar.barlib2.helpera        // Legal!
    from bar.barlib2 import helpera   // Legal!
    
    // You can also import multiple attributes in a single line.
    from bar.barlib2 import (helpera, helperb)
    
    // Note that using from ... import on modules is legal.
    from bar import barlib2           // Legal!
    
Using `importall` is nearly identical to using `import`, though it isn't recommended for standard use. By default, attributes beginning with an underscore aren't included when generating a module. However, using `importall` forces these attributes to be included as well.

## TODO
* Devise rough style guidelines
* Revise modules
* List built-in functions
* Explanation of core module and built-in functions
* Description for creating method hooks in the interpreter
* Add some built-in documentation system like annotations or docstrings