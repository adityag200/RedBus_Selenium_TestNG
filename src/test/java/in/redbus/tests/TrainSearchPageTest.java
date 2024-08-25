package in.redbus.tests;

import com.aventstack.extentreports.Status;
import in.redbus.pages.TrainSearchPage;
import in.redbus.utils.CommonUtils;
import in.redbus.utils.RetryAnalyzer;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;

public class TrainSearchPageTest extends BaseTest {

    private final String sheetName = prop.getProperty("trainSearchPageSheetName");

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
    public void whenUserEntersValidFromAndValidToAndValidDate() throws InterruptedException {

        String testName = "whenUserEntersValidFromAndValidToAndValidDate";

        // ----------------------- Test Starts --------------------------------------------------

        // Fetching all test data from excel file
        HashMap<String, String> testData = new HashMap<String, String>();
        testData = reader.getRowTestData(sheetName, testName);
        String executionRequired = testData.get("Execution Required").toLowerCase();

        String fromLocation = testData.get("From");
        String toLocation = testData.get("To");
        String cancellation = testData.get("CancellationCheck");
        String expectedTitle = testData.get("Expected Title");

        // log all data
        CommonUtils.logTestData(extentTest, sheetName, testName);

        // if execution required field is no
        CommonUtils.toCheckExecutionRequired(executionRequired);

        // Test
        TrainSearchPage trainSearchPage = new TrainSearchPage(driver);

        trainSearchPage.redRailLink();

        trainSearchPage.enterFromLocation(fromLocation);
        logger.info(fromLocation + " was entered");
        extentTest.log(Status.INFO, fromLocation + " was entered");

        trainSearchPage.enterToLocation(toLocation);
        logger.info(toLocation + " was entered");
        extentTest.log(Status.INFO, toLocation + " was entered");

        trainSearchPage.enterCancellationValue(cancellation);
        logger.info(toLocation + " was entered");
        extentTest.log(Status.INFO, toLocation + " was entered");

        logger.info("Today's date was entered");
        extentTest.log(Status.INFO, LocalDate.now().toString().split("-")[2] +" date was entered");

        trainSearchPage.clickOnSearchTrainButton();
        logger.info("search trains button was clicked");
        extentTest.log(Status.INFO, "search buses button was clicked");

        // wait
        new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(StaleElementReferenceException.class).
                until(ExpectedConditions.urlContains("?src="));

        String actualTitle = driver.getTitle().toLowerCase();
        logger.info("ActualTitle :" + actualTitle);
        extentTest.log(Status.INFO, "ActualTitle :" + actualTitle);

        Assert.assertTrue(actualTitle.contains(expectedTitle.toLowerCase()), "Assertion on actual and expected title of search page.");

        // ----------------------- Test Ends --------------------------------------------------
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    public void verifyNoTrainsFoundScreen() throws InterruptedException {

        String testName = "verifyNoTrainsFoundScreen";

        // ----------------------- Test Starts --------------------------------------------------

        // Fetching all test data from excel file
        HashMap<String, String> testData = new HashMap<String, String>();
        testData = reader.getRowTestData(sheetName, testName);
        String executionRequired = testData.get("Execution Required").toLowerCase();

        String fromLocation = testData.get("From");
        String toLocation = testData.get("To");
        String cancellation = testData.get("CancellationCheck");
        String expectedTitle = testData.get("Expected Title");

        // log all data
        CommonUtils.logTestData(extentTest, sheetName, testName);

        // if execution required field is no
        CommonUtils.toCheckExecutionRequired(executionRequired);

        // Test
        TrainSearchPage trainSearchPage = new TrainSearchPage(driver);

        trainSearchPage.redRailLink();

        trainSearchPage.enterFromLocation(fromLocation);
        logger.info(fromLocation + " was entered");
        extentTest.log(Status.INFO, fromLocation + " was entered");

        trainSearchPage.enterToLocation(toLocation);
        logger.info(toLocation + " was entered");
        extentTest.log(Status.INFO, toLocation + " was entered");

        trainSearchPage.enterCancellationValue(cancellation);
        logger.info(toLocation + " was entered");
        extentTest.log(Status.INFO, toLocation + " was entered");

//        trainSearchPage.enterDate();
        logger.info("Today's date was entered");
        extentTest.log(Status.INFO, LocalDate.now().toString().split("-")[2] + 1 +" date was entered");

        trainSearchPage.clickOnSearchTrainButton();
        logger.info("search trains button was clicked");
        extentTest.log(Status.INFO, "search buses button was clicked");

        trainSearchPage.verifyNoTrainFoundScreen();
        logger.info("No Train found screen appeared");
        extentTest.log(Status.INFO, "no train screen verified");

        // wait
        new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(StaleElementReferenceException.class).
                until(ExpectedConditions.urlContains("?src="));

        String actualTitle = driver.getTitle().toLowerCase();
        logger.info("ActualTitle :" + actualTitle);
        extentTest.log(Status.INFO, "ActualTitle :" + actualTitle);

        Assert.assertTrue(actualTitle.contains(expectedTitle.toLowerCase()), "Assertion on actual and expected title of search page.");

        // ----------------------- Test Ends --------------------------------------------------
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
    public void whenUserEntersInvalidFromAndInvalidToAndValidDate() {

        String testName = "whenUserEntersInvalidFromAndInvalidToAndValidDate";

        // ----------------------- Test Starts --------------------------------------------------

        // Fetching all test data from excel file
        HashMap<String, String> testData = new HashMap<String, String>();
        testData = reader.getRowTestData(sheetName, testName);
        String executionRequired = testData.get("Execution Required").toLowerCase();

        String fromLocation = testData.get("From");
        String toLocation = testData.get("To");
        String cancellation = testData.get("CancellationCheck");
        String expectedTitle = testData.get("Expected Title");

        // log all data
        CommonUtils.logTestData(extentTest, sheetName, testName);

        // if execution required field is no
        CommonUtils.toCheckExecutionRequired(executionRequired);

        // Test
        TrainSearchPage trainSearchPage = new TrainSearchPage(driver);

        trainSearchPage.redRailLink();

        trainSearchPage.enterInvalidFromLocation(fromLocation);
        logger.info(fromLocation + " was entered");
        extentTest.log(Status.INFO, fromLocation + " was entered");

        trainSearchPage.enterInvalidToLocation(toLocation);
        logger.info(toLocation + " was entered");
        extentTest.log(Status.INFO, toLocation + " was entered");

        trainSearchPage.enterCancellationValue(cancellation);
        logger.info(toLocation + " was entered");
        extentTest.log(Status.INFO, toLocation + " was entered");

//        trainSearchPage.enterDate();
        logger.info("Today's date was entered");
        extentTest.log(Status.INFO, LocalDate.now().toString().split("-")[2] + 1 +" date was entered");

        trainSearchPage.clickOnSearchTrainButton();
        logger.info("search trains button was clicked");
        extentTest.log(Status.INFO, "search buses button was clicked");

        trainSearchPage.verifyTheAlertOnInvalidInput();

        // wait
        new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(StaleElementReferenceException.class).
                until(ExpectedConditions.urlContains("/railways"));

        String actualTitle = driver.getTitle().toLowerCase();
        logger.info("ActualTitle :" + actualTitle);
        extentTest.log(Status.INFO, "ActualTitle :" + actualTitle);

        Assert.assertTrue(actualTitle.contains(expectedTitle.toLowerCase()), "Assertion on actual and expected title of search page.");

        // ----------------------- Test Ends --------------------------------------------------
    }
    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class)
    public void checkForTheLiveStatus() {

        String testName = "checkForTheLiveStatus";

        // ----------------------- Test Starts --------------------------------------------------

        // Fetching all test data from excel file
        HashMap<String, String> testData = new HashMap<String, String>();
        testData = reader.getRowTestData(sheetName, testName);
        String executionRequired = testData.get("Execution Required").toLowerCase();

        String trainName = testData.get("Train Name");
        String expectedTitle = testData.get("Expected Title");

        // log all data
        CommonUtils.logTestData(extentTest, sheetName, testName);

        // if execution required field is no
        CommonUtils.toCheckExecutionRequired(executionRequired);

        // Test
        TrainSearchPage trainSearchPage = new TrainSearchPage(driver);

        //clicking on the redRail Link
        trainSearchPage.redRailLink();

        //clicking on the Live Train Status toggle
        trainSearchPage.clickOnLiveStatus();

        trainSearchPage.checkForLiveStatus(trainName);
        logger.info(trainName + " was entered to check for Live Status");
        extentTest.log(Status.INFO, trainName + " for live status was entered");

        trainSearchPage.verifyTheLiveStatus();

        // wait
        new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(StaleElementReferenceException.class).
                until(ExpectedConditions.urlContains("/railways"));

        String actualTitle = driver.getTitle().toLowerCase();
        logger.info("ActualTitle :" + actualTitle);
        extentTest.log(Status.INFO, "ActualTitle :" + actualTitle);

        Assert.assertTrue(actualTitle.contains(expectedTitle.toLowerCase()), "Assertion on actual and expected title of search page.");
        Assert.assertTrue(trainSearchPage.verifyTheLiveStatus().contains(trainName), "User is navigated to LTS Screen");

        // ----------------------- Test Ends --------------------------------------------------
    }
}
