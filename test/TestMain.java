import integration.IntegrationSuite;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import performance.PerformanceSuite;
import unit.UnitSuite;

/**
 * Created by rj on 7/12/2016.
 */
public class TestMain {

    private static final String INTEGRATION_TEST = "integration";
    private static final String PERFORMANCE_TEST = "performance";
    private static final String UNIT_TEST = "unit";
    private static final String ALL = "all";

    public static void main(String[] arg) {
        String[] args = new String[1];
        args[0] = UNIT_TEST;
        if (args[0].equalsIgnoreCase(INTEGRATION_TEST)) {
            integrationTest();
        } else if (args[0].equalsIgnoreCase(PERFORMANCE_TEST)) {
            performanceTest();
        } else if (args[0].equalsIgnoreCase(UNIT_TEST)) {
            unitTest();
        } else if (args[0].equalsIgnoreCase(ALL)) {
            integrationTest(); performanceTest(); unitTest();
        } else {
            System.out.println("use either 1. integration \n 2. performance \n 3. unit");
        }
    }

    private static void integrationTest() {
        System.out.println("Running Integration Test");
        test(JUnitCore.runClasses(IntegrationSuite.class));
    }

    private static void performanceTest() {
        System.out.println("Running Performance Test");
        test(JUnitCore.runClasses(PerformanceSuite.class));
    }

    private static void unitTest() {
        System.out.println("Running Unit Test");
        test(JUnitCore.runClasses(UnitSuite.class));
    }

    private static void test(Result result) {
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("RunTime : " + result.getRunTime());
        System.out.println("IgnoreCount : " + result.getIgnoreCount());
        System.out.println((result.wasSuccessful()? "Successful" : "Test failed"));
    }


}
