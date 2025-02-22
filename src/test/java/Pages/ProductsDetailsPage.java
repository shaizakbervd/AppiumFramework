package Pages;

import BaseClass.MenuPage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsDetailsPage extends MenuPage {

    public ProductsDetailsPage()
    {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Sauce Labs Backpack\"]") private WebElement product_title;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"product description\"]") private WebElement product_description;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"product price\"]") private WebElement product_price;

    public String getProductPrice()
    {
        return GetAttribute(product_price, "text");
    }

    public String getProducTitle()
    {
        return GetAttribute(product_title, "text");
    }

    public String getProductDescription()
    {
        return GetAttribute(product_description, "text");
    }
}
