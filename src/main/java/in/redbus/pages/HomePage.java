package in.redbus.pages;

import in.redbus.pagesUtil.HelperClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    public WebDriver driver;
    HelperClass helper = new HelperClass();

    public HomePage(WebDriver driver) {

        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    // Element and Flow for searching buses in home page

    @FindBy(xpath = "//header[@id = 'main_header_new']//img[@class='rb_logo']")
    private WebElement redBusLogo;

    @FindBy(xpath = "//li[@id='bus_tickets_vertical']//span[text()='Bus Tickets']")
    private WebElement busTicketsLink;
    @FindBy(xpath = "//li[@id='rail_tickets_vertical']//span[text()='Train Tickets']")
    private WebElement trainTicketsLink;
    @FindBy(xpath = "//div[@id='autoSuggestContainer']")
    private WebElement searchContainer;
    @FindBy(xpath = "//div[@class='faqQnaContainer']")
    private WebElement faqSection;


    public void clickOnRedBusLogo() {

        redBusLogo.click();
    }

    public void clickOnBusTicketsLink() {

        busTicketsLink.click();
    }

    public boolean verifyRedBusLogo() {
        return redBusLogo.isDisplayed();
    }
    
    public boolean verifyBusTicketsLink() {
        return busTicketsLink.isDisplayed();
    }

    public boolean verifyTrainTicketsLink() {
        return trainTicketsLink.isDisplayed();
    }

    public boolean verifySearchContainer() {
        return searchContainer.isDisplayed();
    }

    public boolean verifyFAQSection() {
        helper.scroll_to_element(driver, faqSection);
        return faqSection.isDisplayed();
    }
}

