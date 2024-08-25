package in.redbus.pages;

import dev.failsafe.internal.util.Assert;
import in.redbus.pagesUtil.HelperClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// includes flow and elements required for search page
public class BusSearchPage {

    public WebDriver driver;

    public BusSearchPage(WebDriver driver) {

        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    // Element and Flow for searching buses in search page

    @FindBy(xpath = "(//div[@id='autoSuggestContainer']//div[@role='button'])[1]")
    private WebElement from;
    @FindBy(xpath = "(//div[@id='autoSuggestContainer']//div[@role='button'])[2]")
    private WebElement to;
    @FindBy(xpath = "//span[@class='dateText']")
    private WebElement date;
    @FindBy(xpath = "//button[@id='search_button']")
    private WebElement searchBusesButton;
    @FindBy(xpath = "(//div[@class='button view-seats fr'][normalize-space()='View Seats'])[1]")
    private WebElement viewSeatsButton;
    @FindBy(xpath = "//div[@id='result-section']")
    private WebElement resultPage;
    @FindBy(xpath = "//div[@class='oops-wrapper new_oops_wrapper']//div[normalize-space() = 'Oops! No buses found.']")
    private WebElement noBusFoundMsg;


    public void enterFromLocation(String fromLocation) {
        from.sendKeys(fromLocation);
        WebElement fromSrc = driver.findElement(By.xpath("(//div[@id='autoSuggestContainer']//div[@role='button'])[1]//text[normalize-space()='"+fromLocation+"']"));
        fromSrc.click();
    }

    public void enterToLocation(String toLocation) {
        to.sendKeys(toLocation);
        WebElement toSrc;
        try{
            toSrc = driver.findElement(By.xpath("//li[contains(@class,'cursorPointing')]//div//text[contains(text(), '"+toLocation+"')]"));
            toSrc.click();
        }catch (NoSuchElementException ignored){
            toSrc = driver.findElement(By.xpath("(//div[@id='autoSuggestContainer']//div[@role='button'])[2]//text[normalize-space()='"+toLocation+"']"));
            toSrc.click();
        }
    }

    public void enterDate() throws InterruptedException {
        Thread.sleep(1000);
        // wait
        try{
            HelperClass.selectDateThroughCalendarOnSearchPage(driver);
        }catch (NoSuchElementException ignored){
            date.click();
            HelperClass.selectDateThroughCalendarOnSearchPage(driver);
        }
    }

    public void clickOnSearchBusesButton() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(StaleElementReferenceException.class).
                    until(ExpectedConditions.visibilityOf(searchBusesButton));
            searchBusesButton.click();
        } catch (NoSuchElementException ignored) {
        }
    }
    public void clickOnViewSeatsBtn() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).ignoring(StaleElementReferenceException.class).
                until(ExpectedConditions.visibilityOf(resultPage));
        viewSeatsButton.click();
    }

    public void verifyNoBusAvailableScreen() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOf(noBusFoundMsg));
        noBusFoundMsg.click();
    }

    public void enterInvalidFromLocation(String fromLocation) {
        from.click();
        from.sendKeys(fromLocation);
    }

    public void enterInvalidToLocation(String toLocation) {
        to.click();
        to.sendKeys(toLocation);
    }

    public boolean checkIfSearchBtnIsClickable() {
        // Check if the button is enabled (clickable)
        return !searchBusesButton.isEnabled();
    }
}

