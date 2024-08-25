package in.redbus.pages;

import in.redbus.pagesUtil.HelperClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TrainSearchPage {
    public WebDriver driver;
    HelperClass helper = new HelperClass();

    public TrainSearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    // Element and Flow for searching trains in search page

    @FindBy(xpath = "//li[@id='rail_tickets_vertical']//span[normalize-space()='Train Tickets']")
    private WebElement redRailLink;
    @FindBy(xpath = "//input[@id='src']")
    private WebElement from;
    @FindBy(xpath = "//div[@class='search-box form-control']//input[@id='dst']")
    private WebElement to;

    //@FindBy(xpath = "//div[@class='date-text']//div[@class='home_date_month']")
    //private WebElement date;
    @FindBy(xpath = "//div[@class='date-text']")
    private WebElement date;
    @FindBy(xpath = "//div[@class='fc_optin_main_wrap_home']//div[@class='checkbox_wrap']")
    private WebElement cancellationCheckBox;
    @FindBy(xpath = "//button[normalize-space()='search trains']")
    private WebElement searchTrainsButton;
    @FindBy(xpath = "//div[@class='ris-wrapper']//div[@class='ris-selector']/p[text()='Live Train Status']")
    private WebElement liveStatusToggle;
    @FindBy(xpath = "//input[@placeholder='Enter train name or number']")
    private WebElement inputTrainName;
    @FindBy(xpath = "//button[normalize-space()='Check Status']")
    private WebElement checkStatusBtn;
    @FindAll({@FindBy(xpath = "//div[@class='error_page_text'][normalize-space() = 'No Trains Found']"),
            @FindBy(xpath = "//div[@class='search_no_direct_train']")})
    private WebElement noTrainFoundMsg;

    public void enterFromLocation(String fromLocation) {
        from.click();
        from.sendKeys(fromLocation);

        // Wait for the selectLocation element to become clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement selectLocation = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='solr_results_block']/div[@class='stn_name_code_wrap']//div[text()='" + fromLocation + "']")));

        try {
            selectLocation.click();
        } catch (StaleElementReferenceException e) {
            // Element is stale, retry clicking
            selectLocation = driver.findElement(By.xpath("//div[@class='solr_results_block']/div[@class='stn_name_code_wrap']//div[text()='" + fromLocation + "']"));
            selectLocation.click();
        }
    }

    public void enterToLocation(String toLocation) throws InterruptedException {
        to.click();
        to.sendKeys(toLocation);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement selectLocation = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='solr_results_block']/div[@class='stn_name_code_wrap']//div[text()='" + toLocation + "']")));
        Thread.sleep(3000);

        try {
            selectLocation.click();
        } catch (StaleElementReferenceException e) {
            // Element is stale, retry clicking
            selectLocation = driver.findElement(By.xpath("//div[@class='solr_results_block']/div[@class='stn_name_code_wrap']//div[text()='" + toLocation + "']"));
            selectLocation.click();
        }
    }

    public void enterDate() throws InterruptedException {
        Thread.sleep(1000);
        // wait
        try {
            date.click();
            Thread.sleep(1000);
            HelperClass.selectDateThroughCalendarOnSearchPage(driver);
            try{
                date.click();
                date.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                HelperClass.selectDateThroughCalendarOnSearchPage(driver);
            }catch(NoSuchElementException ignored){
            }
        } catch (Exception ignored) {
        }
    }

    public void clickOnSearchTrainButton() {
        searchTrainsButton.click();
    }

    public void enterCancellationValue(String cancellation) {
        if (cancellation.equalsIgnoreCase("yes")) {
            cancellationCheckBox.click();
        }
    }

    public void redRailLink() {
        redRailLink.click();
    }

    public void verifyNoTrainFoundScreen() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOf(noTrainFoundMsg));
        noTrainFoundMsg.click();
    }

    public void verifyTheAlertOnInvalidInput() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
    // Switch to the alert
        Alert alert = driver.switchTo().alert();

    // Get the text of the alert
        String alertMessage = alert.getText();

    // Verify the text of the alert
        String expectedAlertMessage = "Please enter valid source and destination";
        if (alertMessage.equals(expectedAlertMessage)) {
            System.out.println("Alert message is correct: " + alertMessage);
        } else {
            System.out.println("Alert message is incorrect: " + alertMessage);
        }

    // Close the alert (optional)
        alert.accept();

    }

    public void enterInvalidFromLocation(String fromLocation) {
        from.click();
        from.sendKeys(fromLocation);
    }

    public void enterInvalidToLocation(String toLocation) {
        to.click();
        to.sendKeys(toLocation);
    }

    public void clickOnLiveStatus() {
        liveStatusToggle.click();
    }

    public void checkForLiveStatus(String trainName) {
        inputTrainName.sendKeys(trainName);
        new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='lts_solr_wrap']//b")));
        driver.findElement(By.xpath("(//div[@class='lts_solr_wrap']//b)[1]")).
                click();
        checkStatusBtn.click();
    }

    public String verifyTheLiveStatus() {
        return driver.findElement(By.xpath("//span[@class='lts_header']")).
                getText();
    }
}
