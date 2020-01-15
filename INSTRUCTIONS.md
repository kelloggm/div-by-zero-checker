CSE 503: Program Analysis

## Assignment 3: Abstract Interpretation Implementation

In the last assignment you designed an abstract interpretation to catch divide-by-zero 
errors. In this assignment you will implement your design (or a simplified version 
of it) for integer division in Java.

#### Implementation. 

You will build your implementation using the [Checker Framework](checkerframework.org), 
a framework that simplifies the task of writing compiler plug-ins for Java.

1. Clone this repository from 
the [GitHub page](https://github.com/kelloggm/div-by-zero-checker). You will need
to have a JDK installed. Your JDK must be either a Java 8 or Java 11 JDK.

2. Verify that everything works by running `./gradlew build` (Linux/Mac) 
or `gradlew.bat build` (Windows). The build should fail with a link to a local
HTML page with information about failing tests. Clicking on "DivideByZeroTest" on that
page should lead to a log containing:

```
java.lang.AssertionError: The test run was expected to issue errors/warnings, but it did not.
0 out of 12 expected diagnostics were found.
12 expected diagnostics were not found:
Foo.java:17: error: divide.by.zero
Foo.java:20: error: divide.by.zero
Foo.java:27: error: divide.by.zero
Foo.java:36: error: divide.by.zero
Foo.java:43: error: divide.by.zero
Foo.java:48: error: divide.by.zero
Foo.java:59: error: divide.by.zero
Foo.java:68: error: divide.by.zero
Foo.java:75: error: divide.by.zero
Foo.java:83: error: divide.by.zero
Foo.java:86: error: divide.by.zero
Foo.java:89: error: divide.by.zero
```

These errors indicate lines in the file `tests/dividebyzero/Foo.java` that ought to
warn about a divide-by-zero error, but do not. That file contains a (non-exhaustive)
selection of Java code that will issue divide by zero errors if executed.

3. Create the lattice structure. The lattice is defined declaratively using files in 
`src/main/java/org/checkerframework/checker/dividebyzero/qual`, one file
per point. Top has been given for you, but you will need to create the others yourself. 
(If you had designed an abstract interpretation with an infinite lattice, you will want 
to compress your design to a finite one.) The 
[Checker Framework manual](https://checkerframework.org/manual/#creating-typequals)
has more information on this if you get stuck.

4. Implement error reporting. The file `DivByZeroVisitor.java` in the main source
directory (`src/main/java/org/checkerframework/checker/dividebyzero`) is responsible for 
reporting errors at specific places in the program once the analysis terminates.

5. At this point, your analysis is functional (although not very useful). Verify that 
errors are reported using `./gradlew build`/`gradlew.bat build`. At this point, instead
of seeing that *not enough* errors are issued on `Foo.java`, you should see that *too many*
error are issued.

6. Implement the abstraction function. The file `DivByZeroAnnotatedTypeFactory.java` 
specifies the rules for what types to attach to constants in the program.

7. Implement refinement rules. The function `refineLhsOfComparison` in the file 
`DivByZeroTransfer.java` specifies the rules for how information is carried into 
`if` statements. For example, the statement
```java
    if (y != 0) {
        System.out.println(x / y);
    }
```
should not report any divide-by-zero errors.

8. Implement transfer functions for arithmetic. 
The function `arithmeticTransfer` in the file `DivByZeroTransfer.java` specifies 
the outputs for arithmetic expressions in terms of points in the lattice. For
example, the statements
```java
   int x = 1 + 0;
   int y = 1 / x;
```
should not report any divide-by-zero errors.

####Use and Writeup. 

To run your analysis, follow the usage instructions in the `README`.
Find a Java project, either one of your own or
open-source, with more than 1000 lines of code. 
Run your analysis on it to find potential divide-by-zero errors.

1. How many potential errors does your analysis report? Estimate how many are false positives.
2. Describe two different potential errors your analysis reported and explain why your tool reported errors
at those locations.
3. Based on what you have found, suggest some potential improvements to your analysis that could make
it more useful.

Submit your assignment to Canvas as a ZIP file containing your source code and a PDF of your writeup.
(You do not need to include the code you tested your analysis on, but you may provide a link if it is
open-source.)

When you submit your completed assignment, please indicate how many hours it took to complete. This
will not factor into your grade.

#### Useful Tips. 

When creating the lattice, you will need to attach annotations to each class:

• Every lattice point class should have 
`@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})`.

• The top of your lattice (and only the top!) should have `@DefaultQualifierInHierarchy`.

• Use `@SubtypeOf({Top.class, Other.class, ...})` to indicate direct subtype relationships.
If you wouldn’t draw those edges in the lattice diagram, you do not need to include 
them in the subtype list.

Functions you need to fill in throughout the code are at the tops of their respective files. 
Helper functions are available in some places, and code you do not need to touch is marked 
as “Checker Framework plumbing.”
However, you are welcome to extend your analysis in any way you choose, should you 
have the time and interest to do so.

