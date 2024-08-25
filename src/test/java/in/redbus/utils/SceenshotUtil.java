package in.redbus.utils;

import in.redbus.tests.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SceenshotUtil extends BaseTest {

    public static String getScreenshot(WebDriver driver, String screenshotName) {

        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
                + ".png";

        // copying the screenshot
        File finalDestination = new File(destination);

        try {
            FileUtils.copyFile(source, finalDestination);
            return destination;
        }
        catch(Exception e) {

            logger.error(e.getMessage());
            return null;
        }
    }
}
