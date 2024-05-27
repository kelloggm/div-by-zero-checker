package org.checkerframework.checker.dividebyzero.qual;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.checkerframework.framework.qual.SubtypeOf;

/**
 * You do not want a qualifier `@NonZero`. It appears here as a placeholder, so that the project
 * compiles.
 */
@SubtypeOf(Top.class)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface NonZero {}
