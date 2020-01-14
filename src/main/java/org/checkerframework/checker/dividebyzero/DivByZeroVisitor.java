package org.checkerframework.checker.dividebyzero;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.framework.source.Result;

import javax.lang.model.type.TypeKind;
import java.lang.annotation.Annotation;
import com.sun.source.tree.*;

import java.util.Set;
import java.util.EnumSet;

import org.checkerframework.checker.dividebyzero.qual.*;

public class DivByZeroVisitor extends BaseTypeVisitor<DivByZeroAnnotatedTypeFactory> {

    /** Set of operators we care about */
    private static final Set<Tree.Kind> DIVISION_OPERATORS = EnumSet.of(
        /* x /  y */ Tree.Kind.DIVIDE,
        /* x /= y */ Tree.Kind.DIVIDE_ASSIGNMENT,
        /* x %  y */ Tree.Kind.REMAINDER,
        /* x %= y */ Tree.Kind.REMAINDER_ASSIGNMENT);

    /**
     * Determine what error (if any) to report at the given binary AST node.
     * @param node the AST node to inspect
     * @return a string to report if there is an error, or null if this node is ok
     */
    private String errorAt(BinaryTree node) {
        // A BinaryTree represents a binary operator, like + or -.
        return null;
    }

    /**
     * Determine what error (if any) to report at the given compound assignment
     * AST node.
     * @param node the AST node to inspect
     * @return a string to report if there is an error, or null if this node is ok
     */
    private String errorAt(CompoundAssignmentTree node) {
        // A CompoundAssignmentTree represents a binary operator plus assignment,
        // like "x += 10".
        return null;
    }

    // ========================================================================
    // Useful helpers

    private static final Set<TypeKind> INT_TYPES = EnumSet.of(
        TypeKind.INT,
        TypeKind.LONG);

    private boolean isInt(Tree node) {
        return INT_TYPES.contains(atypeFactory.getAnnotatedType(node).getKind());
    }

    private boolean hasAnnotation(Tree node, Class<? extends Annotation> c) {
        return atypeFactory.getAnnotatedType(node).hasAnnotation(c);
    }

    // ========================================================================
    // Checker Framework plumbing

    public DivByZeroVisitor(BaseTypeChecker c) {
        super(c);
    }

    @Override
    public Void visitBinary(BinaryTree node, Void p) {
        if (isInt(node)) {
            String err = errorAt(node);
            if (err != null) {
                checker.report(Result.failure(err), node);
            }
        }
        return super.visitBinary(node, p);
    }

    @Override
    public Void visitCompoundAssignment(CompoundAssignmentTree node, Void p) {
        if (isInt(node.getExpression())) {
            String err = errorAt(node);
            if (err != null) {
                checker.report(Result.failure(err), node);
            }
        }
        return super.visitCompoundAssignment(node, p);
    }

}
