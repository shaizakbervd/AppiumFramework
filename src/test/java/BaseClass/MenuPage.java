package BaseClass;

import Pages.SettingsPage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MenuPage extends BaseTest{

    public MenuPage()
    {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView") private WebElement sidebar;

    public SettingsPage Click_SideMenu()
    {
        Click(sidebar);
        return new SettingsPage();
    }
}
