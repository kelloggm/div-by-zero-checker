CSE 503: Software Engineering

## Assignment: Abstract Interpretation Implementation

You already designed an abstract interpretation to catch divide-by-zero 
errors. In this assignment you will implement your design (or a simplified version 
of it) for integer division in Java.

Note that the instructions here are a simplified version of the
[instructions](https://checkerframework.org/manual/#creating-a-checker)
in the Checker Framework manual. In this assignment you won't have to
go through all the steps of creating a checker (we've built a bunch of
starter code for you!), but if you get stuck or run into problems
referring to those instructions is a good idea.

#### Implementation. 

You will build your implementation using the [Checker Framework](https://checkerframework.org/), 
a framework that simplifies the task of writing compiler plug-ins for Java.

1. Clone this repository from 
the [GitHub page](https://github.com/kelloggm/div-by-zero-checker). You will need
to have a JDK installed. Your JDK must be either a Java 8, Java 11, or Java 17 JDK.

2. Check that your setup is correct by running `./gradlew build` (Linux/Mac) 
or `gradlew.bat build` (Windows). The build should fail with a link to a local
HTML page with information about failing tests, a stack trace, and a list of failures.
By either clicking on "DivideByZeroTest" on the HTML page or by looking at the list of failures 
printed to the console, make sure that you can see a log containing:

```
java.lang.AssertionError: The test run was expected to issue errors/warnings, but it did not.
    0 out of 12 expected diagnostics were found.
    12 expected diagnostics were not found:
    Foo.java:16: error: divide.by.zero
    Foo.java:19: error: divide.by.zero
    Foo.java:26: error: divide.by.zero
    Foo.java:35: error: divide.by.zero
    Foo.java:42: error: divide.by.zero
    Foo.java:47: error: divide.by.zero
    Foo.java:58: error: divide.by.zero
    Foo.java:67: error: divide.by.zero
    Foo.java:74: error: divide.by.zero
    Foo.java:82: error: divide.by.zero
    Foo.java:85: error: divide.by.zero
    Foo.java:88: error: divide.by.zero
```

  These errors indicate lines in the file `tests/dividebyzero/Foo.java` that ought to
  warn about a divide-by-zero error, but do not. That file contains a
  selection of Java code that will issue divide by zero errors if executed. The test code
  in `Foo.java` is non-exhaustive: you are expected to write your own test code to
  augment it. You can do so by either adding code to `Foo.java` or by adding new `.java`
  files to the `tests/dividebyzero/` directory. We will not grade your test suite directly,
  but writing a higher-quality test suite will help you avoid making mistakes in defining
  your analysis.

3. Create the lattice structure. The lattice is defined declaratively using files in 
`src/main/java/org/checkerframework/checker/dividebyzero/qual`, one file
per point. Top has been given for you, but you will need to create the others yourself. 
(If you had designed an abstract interpretation with an infinite lattice, you will want 
to compress your design to a finite one.) The 
[Checker Framework manual](https://checkerframework.org/manual/#creating-typequals)
has more information on this if you get stuck.

  When creating the lattice, you will need to attach annotations to each class:
  * Every lattice point class should have
    `@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})`.
  * The top of your lattice (and only the top!) should have `@DefaultQualifierInHierarchy`.
  * Use `@SubtypeOf({Top.class, Other.class, ...})` to indicate direct subtype relationships.
    If you wouldn't draw those edges in the lattice diagram, you do not need to include
    them in the subtype list.

4. Implement error reporting. The file `DivByZeroVisitor.java` in the main source
directory (`src/main/java/org/checkerframework/checker/dividebyzero`) is responsible for 
reporting errors at specific places in the program once the analysis terminates.

5. At this point, your analysis is functional (although not very useful). Verify that 
errors are reported using `./gradlew build`/`gradlew.bat build`. At this point, instead
of seeing that *not enough* errors are issued on `Foo.java`, you should see that *too many*
errors are issued.

6. Implement the abstraction function. The file `DivByZeroAnnotatedTypeFactory.java` 
specifies the rules for what types to attach to constants in the program.

7. Implement transfer functions for arithmetic. 
The function `arithmeticTransfer` in the file `DivByZeroTransfer.java` specifies 
the outputs for arithmetic expressions in terms of points in the lattice. For
example, the statements
```java
   int x = 1 + 0;
   int y = 1 / x;
```
should not report any divide-by-zero errors.

8. Implement refinement rules. The function `refineLhsOfComparison` in the file
   `DivByZeroTransfer.java` specifies the rules for how information is carried into
   `if` statements. For example, the statement
```java
    if (y != 0) {
        System.out.println(x / y);
    }
```
should not report any divide-by-zero errors.

#### Useful Tips.

Functions you need to fill in throughout the code are at the tops of their respective files. 
Helper functions are available in some places, and code you do not need to touch is marked 
as “Checker Framework plumbing.”

However, you are welcome to extend your analysis in any way you choose, should you 
have the time and interest to do so.

