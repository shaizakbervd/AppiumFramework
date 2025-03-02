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
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"menu item log out\"]") private WebElement logoutbtn;
    @AndroidFindBy (xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]") private WebElement confirmlogoutbtn;
    @AndroidFindBy (xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]") private WebElement confirmlogoutbtnOk;



    public LoginPage Click_Login()
    {
        Click(loginbtn, "clicking login");
        return new LoginPage();
    }

    public LoginPage Click_Logout()
    {
        Click(logoutbtn, "click logout button");
        Click(confirmlogoutbtn, "click confirm logout button");
        Click(confirmlogoutbtnOk, "ok button pressed");
        return new LoginPage();
    }

}
