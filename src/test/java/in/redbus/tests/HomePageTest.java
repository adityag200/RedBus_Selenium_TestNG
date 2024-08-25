package in.redbus.tests;

import in.redbus.pages.HomePage;
import in.redbus.utils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest{

    private final String sheetName = prop.getProperty("homePageSheetName");

    @Test(retryAnalyzer = RetryAnalyzer.class)
    private void verifyTheHomePageOfRedBus() {

        String testName = "verifyTheHomePageOfRedBus";

        // ----------------------- Test Starts --------------------------------------------------
        // start the extent report
//        extentTest = extentReport.createTest(testName, testName+" started");

        // Test
        HomePage homePage = new HomePage(driver);

        //verifying the redbus logo
        logger.info("verifying logo of RedBus is present");
        Assert.assertTrue(homePage.verifyRedBusLogo(), "Redbus logo present");

        //verify the bus tickets and train tickets links
        logger.info("verifying the bus tickets and train tickets links");
        Assert.assertTrue(homePage.verifyBusTicketsLink(), "Bus tickets link present");
        Assert.assertTrue(homePage.verifyTrainTicketsLink(), "Train tickets link present");

        //verify the search container
        logger.info("verifying the search container");
        Assert.assertTrue(homePage.verifySearchContainer(), "Search container present");

        //verify The FAQ Section
        logger.info("Verifying the FAQ section");
        Assert.assertTrue(homePage.verifyFAQSection(), "FAQ section present");

    }
}
