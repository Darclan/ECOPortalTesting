package eco;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LoginPage {
    WebDriver driver;
    WebElement element;

    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        //this.driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
    }

    private WebElement getUserNameField() {
        return this.driver.findElement(By.id("username"));
    }

    private WebElement getPasswordField() {
        return this.driver.findElement(By.id("password"));
    }

    private WebElement getSubmitButton() {
        return this.driver.findElement(By.xpath("//button[@type='submit' and not(@class='bv-hidden-submit')]"));
    }

    private WebElement getLanguageSelectionDropdown() {
        return this.driver.findElement(By.xpath("//select[@id='lang-selector']"));
    }

    private WebElement getLanguageSelectionOption(String language) {
        return this.driver.findElement(By.xpath("//option[contains(text(), '"+language+"')]"));
    }

    public void fillUserName(String username){
        WebDriverWait wait = new WebDriverWait(this.driver, 15);
        wait.until(ExpectedConditions.visibilityOf(getUserNameField()));
        this.element = getUserNameField();
        this.element.sendKeys(username);
    }

    public void fillPassword(String password){
        this.element = getPasswordField();
        this.element.sendKeys(password);
    }

    public void selectLanguage(String language){
        if (!language.equals("")){
            try
            {
                WebDriverWait wait_lang = new WebDriverWait(this.driver, 25);
                wait_lang.until(ExpectedConditions.visibilityOf(getLanguageSelectionDropdown()));
                getLanguageSelectionDropdown().click();
                getLanguageSelectionOption(language).click();
            }
            catch (NoSuchElementException exception)
            {
                //do nothing
            }
        }
    }

    public void clickSubmit(){
        getSubmitButton().click();
    }
}
