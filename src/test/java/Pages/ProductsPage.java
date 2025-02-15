package Pages;

import BaseClass.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends BaseTest {

    public ProductsPage()
    {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView") private WebElement sidebar;
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"menu item log in\"]") private WebElement loginbtn;
    @AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Products\"]") private WebElement products_txt;

    public String getTitle()
    {
        return GetAttribute(products_txt, "text");
    }

    public ProductsPage Click_Sidemenu()
    {
        Click(sidebar);
        return this;
    }

    public LoginPage Click_Login()
    {
        Click(loginbtn);
        return new LoginPage();
    }

}
