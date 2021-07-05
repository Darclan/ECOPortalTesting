package eco;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;

public class ProductPage {
    WebDriver driver;
    String url;

    public ProductPage(WebDriver driver, String basicPageURL) {
        this.driver = driver;
        this.url = basicPageURL;
    }

    public void checkListOfProductDisplay(List<String> productTable) {
        for(int i=0; i<productTable.size(); ++i) {
            driver.get(url+"/p/"+productTable.get(i));
            String loadedUrl = driver.getCurrentUrl();
            if(loadedUrl.contains("page-not-found"))
            {
                System.out.println("Page 404 is displayed, product not found");
            }
            else if (loadedUrl.contains(productTable.get(i)))
            {
                System.out.println("Product page is displayed");
                WebElement productDetailsPageProductNumber = driver.findElement(By.xpath("//*[@id='productDetailsPage']/div[1]/div/span"));
                System.out.println("Product number shown as "+productDetailsPageProductNumber.getText().trim().replaceAll("\\D+", ""));
            }
        }
    }

    public Boolean checkSingleProductDisplay(String product) {
        Boolean status = false;
        this.driver.get(url+"/p/"+product);
        String loadedUrl = this.driver.getCurrentUrl();
        if(loadedUrl.contains("page-not-found"))
        {
            System.out.println("Page 404 is displayed, product not found");
            status = false;
        }
        else if (loadedUrl.contains(product))
        {
            System.out.println("Product page is displayed");
            WebElement productDetailsPageProductNumber = driver.findElement(By.xpath("//*[@id='productDetailsPage']/div[1]/div/span"));
            System.out.println("Product number shown as "+productDetailsPageProductNumber.getText().trim().replaceAll("\\D+", ""));
            status = true;
        }
        return status;
    }
}
