package Pages;

import BaseClass.BaseTest;
import BaseClass.MenuPage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends MenuPage {

    public ProductsPage()
    {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Products\"]") private WebElement products_txt;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"store item text\" and @text=\"Sauce Labs Backpack\"]") private WebElement product_title;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"store item price\" and @text=\"$29.99\"]") private WebElement product_price;

    public String getTitle()
    {
        return GetAttribute(products_txt, "text");
    }

    public String getProductTitle()
    {
        return GetAttribute(product_title, "text");
    }

    public String getProducPrice()
    {
        return GetAttribute(product_price, "text");
    }

    public ProductsDetailsPage Click_Product()
    {
        Click(product_title);
        return new ProductsDetailsPage();
    }
}
