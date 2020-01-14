package org.checkerframework.checker.dividebyzero;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.qual.RelevantJavaTypes;

@RelevantJavaTypes({Integer.class, Long.class})
public class DivByZeroChecker extends BaseTypeChecker {
}
