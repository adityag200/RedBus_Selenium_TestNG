package in.redbus.tests;

import com.aventstack.extentreports.Status;
import in.redbus.pages.BusSearchPage;
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

public class BusSearchPageTest extends BaseTest {


    // Worksheet Name used by all tests
    private final String sheetName = prop.getProperty("searchPageSheetName");


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
        String expectedTitle = testData.get("Expected Title");

        // log all data
        CommonUtils.logTestData(extentTest, sheetName, testName);

        // if execution required field is no
        CommonUtils.toCheckExecutionRequired(executionRequired);

        // Test
        BusSearchPage busSearchPage = new BusSearchPage(driver);

        busSearchPage.enterFromLocation(fromLocation);
        logger.info(fromLocation + " was entered");
        extentTest.log(Status.INFO, fromLocation + " was entered");

        busSearchPage.enterToLocation(toLocation);
        logger.info(toLocation + " was entered");
        extentTest.log(Status.INFO, toLocation + " was entered");

        busSearchPage.enterDate();
        logger.info("Today's date was entered");
        extentTest.log(Status.INFO, LocalDate.now().toString().split("-")[2] +" date was entered");

        busSearchPage.clickOnSearchBusesButton();
        logger.info("search buses button was clicked");
        extentTest.log(Status.INFO, "search buses button was clicked");

        // wait
        new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(StaleElementReferenceException.class).
                until(ExpectedConditions.urlContains("fromCity"));

        String actualTitle = driver.getTitle().toLowerCase();
        logger.info("ActualTitle :" + actualTitle);
        extentTest.log(Status.INFO, "ActualTitle :" + actualTitle);

        Assert.assertTrue(actualTitle.contains(expectedTitle.toLowerCase()), "Assertion on actual and expected title of search page.");

        // ----------------------- Test Ends --------------------------------------------------
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    public void userSelectsSearchResultFromList() throws InterruptedException {
        String testName = "userSelectsSearchResultFromList";

        HashMap<String, String> testData = new HashMap<String, String>();
        testData = reader.getRowTestData(sheetName, testName);
        String executionRequired = testData.get("Execution Required").toLowerCase();

        String fromLocation = testData.get("From");
        String toLocation = testData.get("To");
        String expectedTitle = testData.get("Expected Title");

        // log all data
        CommonUtils.logTestData(extentTest, sheetName, testName);

        // if execution required field is no
        CommonUtils.toCheckExecutionRequired(executionRequired);

        BusSearchPage busSearchPage = new BusSearchPage(driver);

        busSearchPage.enterFromLocation(fromLocation);
        logger.info(fromLocation + " was entered");
        extentTest.log(Status.INFO, fromLocation + " was entered");

        busSearchPage.enterToLocation(toLocation);
        logger.info(toLocation + " was entered");
        extentTest.log(Status.INFO, toLocation + " was entered");

        busSearchPage.enterDate();
        logger.info("Today's date was entered");
        extentTest.log(Status.INFO, LocalDate.now().toString().split("-")[2] +" date was entered");

        busSearchPage.clickOnSearchBusesButton();
        logger.info("search buses button was clicked");
        extentTest.log(Status.INFO, "search buses button was clicked");

        // wait
        new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(StaleElementReferenceException.class).
                until(ExpectedConditions.urlContains("fromCity"));

        String actualTitle = driver.getTitle().toLowerCase();
        logger.info("ActualTitle :" + actualTitle);
        extentTest.log(Status.INFO, "ActualTitle :" + actualTitle);


        // Test
        busSearchPage.clickOnViewSeatsBtn();
        Assert.assertTrue(actualTitle.contains(expectedTitle.toLowerCase()), "Assertion on actual and expected title of search page.");
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
    public void verifyNoBusAvailableScreen() throws InterruptedException {

        String testName = "verifyNoBusAvailableScreen";
        // ----------------------- Test Starts --------------------------------------------------

        // Fetching all test data from excel file
        HashMap<String, String> testData = new HashMap<String, String>();
        testData = reader.getRowTestData(sheetName, testName);
        String executionRequired = testData.get("Execution Required").toLowerCase();

        String fromLocation = testData.get("From");
        String toLocation = testData.get("To");
        String expectedTitle = testData.get("Expected Title");

        // log all data
        CommonUtils.logTestData(extentTest, sheetName, testName);

        // if execution required field is no
        CommonUtils.toCheckExecutionRequired(executionRequired);

        // Test
        BusSearchPage busSearchPage = new BusSearchPage(driver);

        busSearchPage.enterFromLocation(fromLocation);
        logger.info(fromLocation+" was entered");
        extentTest.log(Status.INFO, fromLocation+" was entered");

        busSearchPage.enterToLocation(toLocation);
        logger.info(toLocation+" was entered");
        extentTest.log(Status.INFO, toLocation+" was entered");

        busSearchPage.enterDate();
        logger.info("Today's date was entered");
        extentTest.log(Status.INFO, LocalDate.now().toString().split("-")[2]+" date was entered");


        busSearchPage.clickOnSearchBusesButton();
        logger.info("search buses button was clicked");
        extentTest.log(Status.INFO, "search buses button was clicked");

        busSearchPage.verifyNoBusAvailableScreen();

        String actualTitle = driver.getTitle().toLowerCase();
        logger.info("Title :"+actualTitle);
        extentTest.log(Status.INFO, "Title :"+actualTitle);

        Assert.assertTrue(actualTitle.contains(expectedTitle.toLowerCase()), "Assertion on actual and expected title of search page.");

        // ----------------------- Test Ends --------------------------------------------------
    }


    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class)
    public void whenUserEntersInvalidFromAndInvalidToAndValidDate() throws InterruptedException {

        String testName = "whenUserEntersInvalidFromAndInvalidToAndValidDate";
        // ----------------------- Test Starts --------------------------------------------------

        // Fetching all test data from excel file
        HashMap<String, String> testData = new HashMap<String, String>();
        testData = reader.getRowTestData(sheetName, testName);
        String executionRequired = testData.get("Execution Required").toLowerCase();

        String fromLocation = testData.get("From");
        String toLocation = testData.get("To");

        // log all data
        CommonUtils.logTestData(extentTest, sheetName, testName);

        // if execution required field is no
        CommonUtils.toCheckExecutionRequired(executionRequired);

        // Test
        BusSearchPage busSearchPage = new BusSearchPage(driver);

        busSearchPage.enterFromLocation(fromLocation);
        logger.info(fromLocation+" was entered");
        extentTest.log(Status.INFO, fromLocation+" was entered");

        busSearchPage.enterToLocation(toLocation);
        logger.info(toLocation+" was entered");
        extentTest.log(Status.INFO, toLocation+" was entered");

        busSearchPage.enterDate();
        logger.info("Today's date was entered");
        extentTest.log(Status.INFO, LocalDate.now().toString().split("-")[2]+" date was entered");

        busSearchPage.checkIfSearchBtnIsClickable();
        logger.info("Search Button is not clickable");
        extentTest.log(Status.INFO, "search buses button is not clickable");

        String actualTitle = driver.getTitle().toLowerCase();
        logger.info("Title :"+actualTitle);
        extentTest.log(Status.INFO, "Title :"+actualTitle);

        Assert.assertFalse(busSearchPage.checkIfSearchBtnIsClickable(), "Search Btn is not clickable");

        // ----------------------- Test Ends --------------------------------------------------
    }
}