package in.redbus.pagesUtil;

import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HelperClass{

    // To select date through calendar on search page
    public static void selectDateThroughCalendarOnSearchPage(WebDriver driver) {

        LocalDate today = LocalDate.now();

        // Date Format DD-MM YEAR
        String[] dateValue = today.toString().split("-");

        String date = dateValue[2];

        WebElement calDate;
        boolean requiredDate = false;

        while (!requiredDate) {

            calDate = driver.findElement(By.xpath("//*[text()='" + date + "']"));
            calDate.click();
            requiredDate = true;
        }

    }

    // selecting proper frame
    public static int selectRequiredIframe(WebDriver driver, String xpathLocator) {
        List<WebElement> frames = driver.findElements(By.tagName("iframe"));

        System.out.println("Total Frames : " + frames.size());

        for (int index = 0; index < frames.size(); index++) {
            driver.switchTo().frame(index);
            List<WebElement> elements = driver.findElements(By.xpath(xpathLocator));

            if (!elements.isEmpty()) {
                // If the element is found in the frame, return the frame index
                driver.switchTo().defaultContent();
                return index;
            }
            driver.switchTo().defaultContent();
        }

        // If no frame contains the element, return -1
        return -1;
    }

    public void scroll_to_element(WebDriver driver,WebElement element) {
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView();",element);
    }
}

