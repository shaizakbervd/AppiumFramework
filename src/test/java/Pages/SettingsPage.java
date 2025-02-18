package Pages;

import BaseClass.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsPage extends BaseTest {

    public SettingsPage()
    {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"menu item log in\"]") private WebElement loginbtn;



    public LoginPage Click_Login()
    {
        Click(loginbtn);
        return new LoginPage();
    }

}
