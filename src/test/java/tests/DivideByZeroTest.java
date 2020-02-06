import java.io.File;
import java.util.List;
import org.checkerframework.checker.dividebyzero.DivByZeroChecker;
import org.checkerframework.framework.test.CheckerFrameworkPerDirectoryTest;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test runner for tests of the Divide By Zero Checker.
 *
 * <p>Tests appear as Java files in the {@code tests/dividebyzero} folder. To add a new test case,
 * create a Java file in that directory. The file contains "// ::" comments to indicate expected
 * errors and warnings; see
 * https://github.com/typetools/checker-framework/blob/master/checker/tests/README .
 */
public class DivideByZeroTest extends CheckerFrameworkPerDirectoryTest {
    public DivideByZeroTest(List<File> testFiles) {
        super(
                testFiles,
                DivByZeroChecker.class,
                "dividebyzero",
                "-Anomsgtext",
                "-Astubs=stubs/",
                "-nowarn");
    }

    @Parameters
    public static String[] getTestDirs() {
        return new String[] {"dividebyzero"};
    }
}
