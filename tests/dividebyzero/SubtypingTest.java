import org.checkerframework.checker.dividebyzero.qual.*;

// Test basic subtyping relationships for the Divide By Zero Checker.
class SubtypeTest {
    void allSubtypingRelationships(@DivideByZeroUnknown int x, @DivideByZeroBottom int y) {
        @DivideByZeroUnknown int a = x;
        @DivideByZeroUnknown int b = y;
        // :: error: assignment.type.incompatible
        @DivideByZeroBottom int c = x; // expected error on this line
        @DivideByZeroBottom int d = y;
    }
}
