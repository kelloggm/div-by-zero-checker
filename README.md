# This repository

This repository contains a template for an abstract interpretation implementation
assignment. You can find the full instructions for the assignment in `INSTRUCTIONS.md`
in this directory, or on your course webpage.

The remainder of this README is the user-facing documentation for the analysis
you will build.

You should remove, from your version, everything above and including this line.


# The Divide By Zero Checker

A common problem when programming is division by zero.
This results in a run-time exception.

The Divide By Zero Checker guarantees, at compile time, that your code will
not suffer that run-time exception.


## How to run the checker

Java 11 or later is required.

First, publish the checker to your local Maven repository by running
`./gradlew publishToMavenLocal` in this repository.

Then, if you use Gradle, add the following to the `build.gradle` file in
the project you wish to type-check (using Maven is similar):

```
repositories {
    mavenLocal()
    mavenCentral()
}
dependencies {
    annotationProcessor 'org.checkerframework:dividebyzero-checker:0.1-SNAPSHOT'
}
```

Now, when you build your project, the Divide By Zero Checker will also run,
informing you of any potential errors related to division by zero.


## How to specify your code

At compile time, the Divide By Zero Checker estimates what values the program
may compute at run time.  It issues a warning if the program may
perform a division by zero.
It works via a technique called pluggable typechecking.

You need to specify the contracts of methods and fields in your code --
that is, their requirements and their guarantees.  The Divide By Zero Checker
ensures that your code is consistent with the contracts, and that the
contracts guarantee that the program never divides by zero.

You specify your code by writing *qualifiers* such as `@YourQualifierNameHere`
on types, to indicate more precisely what values the type represents.
Here is a list of the type qualifiers that are supported by
the Divide By Zero Checker, with an explanation of each one:

TODO.


## How to build the checker

Run these commands from the top-level directory.

`./gradlew build`: build the checker

`./gradlew publishToMavenLocal`: publish the checker to your local Maven repository.
This is useful for testing before you publish it elsewhere, such as to Maven Central.


## More information

The Divide By Zero Checker is built upon the Checker Framework.  Please see
the [Checker Framework Manual](https://checkerframework.org/manual/) for
more information about using pluggable type-checkers, including this one.
