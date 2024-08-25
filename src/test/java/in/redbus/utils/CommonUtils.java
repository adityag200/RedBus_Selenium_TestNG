package in.redbus.utils;

import com.aventstack.extentreports.Status;
import in.redbus.tests.BaseTest;
import org.testng.SkipException;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;

public class CommonUtils extends BaseTest {

//    public static ExtentTest extentTest = null;

    public static void logTestData(ExtentTest extentTest, String worksheetName, String testCaseName) {
        HashMap<String, String> testData = new HashMap<String, String>();
        testData = reader.getRowTestData(worksheetName, testCaseName);

        //print all data
        extentTest.log(Status.INFO, "--------------------------- Test Data -------------------------------");

        logger.info("Test Data For Worksheet Name " + worksheetName + " And For Test Case " + testCaseName);
        extentTest.log(Status.INFO, "Test Data For Worksheet Name " + worksheetName + " And For Test Case " + testCaseName);

        for(HashMap.Entry<String, String> entry: testData.entrySet()) {
            if(entry.getKey() != "" && entry.getValue() != "") {
                logger.info(entry.getKey() + " : " + entry.getValue().toString());
                extentTest.log(Status.INFO, entry.getKey() + " : " + entry.getValue().toString());
            }
        }
    }

    public static void toCheckExecutionRequired(String executionRequired){
        if(executionRequired.equals("no")) {
            extentTest.log(Status.WARNING, "Execution Required : " + executionRequired.toUpperCase());
            logger.info("Execution required is no , skipping the test");
            throw new SkipException("Skipping this exception");
        }

        if (executionRequired.equals("")) {
            extentTest.log(Status.WARNING, "Execution Required : " + executionRequired.toUpperCase());
            logger.info("Execution required is empty , skipping the test");
            throw new SkipException("Skipping this exception");
        }
    }
}
