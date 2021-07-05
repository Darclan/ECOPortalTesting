package eco;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;

public class SoldToSelectionPage {
    WebDriver driver;

    public SoldToSelectionPage(WebDriver driver){
        this.driver = driver;
    }

    private WebElement getSoldToPartySelectionTable(){
        return this.driver.findElement(By.xpath("//table[contains(@class, 'loginSoldtoTable')]"));
    }

    private WebElement getSoldToNumberCell(String number){
        return this.driver.findElement(By.xpath("//td[contains(text(), '"+number+"')]"));
    }

    private WebElement getContinueButtonCorrespondingToSTP(String number){
        return this.driver.findElement(By.xpath("//input[contains(@class, 'soldToContinue_"+number+"')]"));
    }

    public void selectSoldToParty(String stp){
        try
        {
            WebDriverWait wait_stsp = new WebDriverWait(this.driver, 5);
            wait_stsp.until(ExpectedConditions.elementToBeClickable(getSoldToPartySelectionTable()));
            if(getSoldToPartySelectionTable().isDisplayed()){
                if(!stp.equals("")){
                    getSoldToNumberCell(stp).click();
                    wait_stsp.until(ExpectedConditions.elementToBeClickable(getContinueButtonCorrespondingToSTP(stp)));
                    getContinueButtonCorrespondingToSTP(stp).click();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Select STP, then click OK");
                }
            }
        }
        catch (NoSuchElementException exception)
        {
            //do nothing
        }
    }
}
