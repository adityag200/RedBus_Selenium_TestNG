package in.redbus.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.MediaEntityBuilder;
import in.redbus.utils.ExcelFileIO;
import in.redbus.utils.SceenshotUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    // To initialize, select( Chrome, IE), open and quit browser.
    public static WebDriver driver;

    public static Properties prop = new Properties();
    public static File file = new File("src/main/resources/config.properties");
    public static FileInputStream fis = null;

    // Excel File Test Data Reader
    public static ExcelFileIO reader = null;

    // Log4j logger
    public final static Logger logger = Logger.getLogger(BaseTest.class);

    // Extent Report
    public static ExtentReports extentReport = null;
    public static ExtentTest extentTest = null;

    // Creating and using properties file
    static {

        // Exception Handling for FIS
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }

        // Exception Handling for Prop
        try {

            prop.load(fis);
        } catch (IOException e) {

            logger.error(e.getMessage());
        }

        // Exception Handling for Excel File
        try {

            reader = new ExcelFileIO(prop.getProperty("testDataFileLocation"));
        } catch (Exception e) {

            logger.error(e.getMessage());
        }
    }

    private static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return sdf.format(new Date());
    }

    private static void archiveResults() {
        try {
            String currentDateTime = getCurrentDateTime();
            File currentTestResults = new File("CurrentTestResults/Report/RedBusAutomationExtentReport.html");
            File archivedTestResults = new File("ArchievedResults/" + currentDateTime+".html");
            Files.move(Paths.get(currentTestResults.getAbsolutePath()), Paths.get(archivedTestResults.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cleanCurrentTestResults() {
        File currentTestResults = new File("CurrentTestResults/logs/");
        if (currentTestResults.exists()) {
            for (File file : currentTestResults.listFiles()) {
                file.delete();
            }
        }
    }



    @BeforeTest
    public void createReport() {
        // Setting up extent report
        String sheetName = prop.getProperty("extentReportSystemInfoSheetName");
        String id = "Test";

        HashMap<String, String> systemInfo = reader.getRowTestData(sheetName, id);

        extentReport = new ExtentReports();
        extentReport.setSystemInfo("Environment", systemInfo.get("Environment"));
        extentReport.setSystemInfo("Browser", systemInfo.get("Browser"));
        // Add more system information as needed...

        // Set report output location
        extentReport.setReportUsesManualConfiguration(true);

        // Start the report with the desired file name
        extentReport.attachReporter(new ExtentHtmlReporter(prop.getProperty("reportOutputLocation") + prop.getProperty("reportName")));

        //archiving old reports
        archiveResults();

        //cleaning logs
        cleanCurrentTestResults();

        // Flush the report
        extentReport.flush();
    }

    @BeforeMethod
    public void initializeExtentTest(Method method) {
        // Create a new ExtentTest object for each test method
        extentTest = extentReport.createTest(method.getName());
    }


    public ExtentHtmlReporter createExtentHtmlReporter(String reportLocation) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportLocation);
        // Additional configuration if needed
        return htmlReporter;
    }

    @BeforeMethod
    public void initDriver() throws MalformedURLException {
        boolean headlessMode = Boolean.parseBoolean(prop.getProperty("headlessMode"));
        String browser = prop.getProperty("browser");
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", prop.getProperty("driversLocation") + "chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headlessMode) {
                    chromeOptions.addArguments("--headless");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", prop.getProperty("driversLocation") + "msedgedriver.exe");
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headlessMode) {
                    // Headless mode is not supported in Edge
                    // You can add other options if needed
                }
                driver = new EdgeDriver(edgeOptions);
                break;
            // Add cases for other browsers if needed
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    @BeforeMethod(dependsOnMethods = "initDriver")
    public void setUp() {
        // Your setup code remains the same
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty("globalWait")), TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(prop.getProperty("testUrl"));
        logger.info(prop.getProperty("testUrl") + " page opened...");
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        // for fail test cases
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, "TEST FAILED " + result.getName());
            extentTest.log(Status.FAIL, "TEST FAILED THROWABLE EXC " + result.getThrowable());

            // adding screenshot
            String screenshotPath = SceenshotUtil.getScreenshot(driver, result.getName());
            extentTest.fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, "TEST SKIPPED " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, "TEST PASSED " + result.getName());
        }

        // end the testcase in the extent report
        extentReport.flush();

        // closing the driver
        driver.quit();
        logger.info("Browser closed...");
    }



    @AfterTest
    public void endReport() {
        // tearing down extent report
        extentReport.flush();
    }
}
