package Pages;

import BaseClass.BaseTest;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseTest
{

    public LoginPage()
    {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    @AndroidFindBy (accessibility = "Username input field") private WebElement username;
    @AndroidFindBy (accessibility = "Password input field") private WebElement password;
    @AndroidFindBy (accessibility = "Login button") private WebElement loginbtn;
    @AndroidFindBy (xpath = "//android.widget.TextView[@text=\"Provided credentials do not match any user in this service.\"]") private WebElement error_msg;


    public LoginPage EnterUserName(String usernamevalue)
    {
        SendKeys(username, usernamevalue);
        return this;
    }
    public LoginPage EnterPassword(String passwordvalue)
    {
        SendKeys(password, passwordvalue);
        return this;
    }

    public LoginPage ClearFields()
    {
        Clear_Fields(username, password);
        return this;
    }

    public String Get_ErrorText()
    {
       return GetAttribute(error_msg, "text");
    }

    public ProductsPage Click_Login()
    {
        Click(loginbtn);
        return new ProductsPage();
    }

}

