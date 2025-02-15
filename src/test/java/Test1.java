//import io.appium.java_client.AppiumBy;
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.android.AndroidDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.testng.Assert;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.time.Duration;
//
//public class Test1 {
//
//    AppiumDriver driver;
//
//    @Test(priority = 1)
//    public void InvalidUsername()
//    {
//        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]/android.widget.ImageView")).click();
//        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"menu item log in\"]")).click();
//
//        WebElement username = driver.findElement(AppiumBy.accessibilityId("Username input field"));
//        WebElement password = driver.findElement(AppiumBy.accessibilityId("Password input field"));
//        WebElement loginbtn = driver.findElement(AppiumBy.accessibilityId("Login button"));
//
//        username.sendKeys("hello@example.com");
//        password.sendKeys("10203040");
//        loginbtn.click();
//
//        String error_message = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Provided credentials do not match any user in this service.\"]")).getText();
//        String expected_error = "Provided credentials do not match any user in this service.";
//        Assert.assertEquals(error_message, expected_error);
//        username.clear();
//        password.clear();
//    }
//
//    @Test(priority = 2)
//    public void invalidPassword()
//    {
//        WebElement username = driver.findElement(AppiumBy.accessibilityId("Username input field"));
//        WebElement password = driver.findElement(AppiumBy.accessibilityId("Password input field"));
//        WebElement loginbtn = driver.findElement(AppiumBy.accessibilityId("Login button"));
//
//        username.sendKeys("bob@example.com");
//        password.sendKeys("10203043");
//        loginbtn.click();
//
//        String error_message = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Provided credentials do not match any user in this service.\"]")).getText();
//        String expected_error = "Provided credentials do not match any user in this service.";
//        Assert.assertEquals(error_message, expected_error);
//        username.clear();
//        password.clear();
//    }
//
//    @Test(priority = 3)
//    public void ValidLogin()
//    {
//
//        WebElement username = driver.findElement(AppiumBy.accessibilityId("Username input field"));
//        WebElement password = driver.findElement(AppiumBy.accessibilityId("Password input field"));
//        WebElement loginbtn = driver.findElement(AppiumBy.accessibilityId("Login button"));
//
//        username.sendKeys("bob@example.com");
//        password.sendKeys("10203040");
//        loginbtn.click();
//
//        String products = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Products\"]")).getText();
//        String expected_error = "Products";
//        Assert.assertEquals(products, expected_error);
//    }
//
//    @BeforeClass
//    public void BeforeClassHook() throws MalformedURLException {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("appium:automationName", "uiautomator2");
//        capabilities.setCapability("appium:deviceName", "pixel_7");
//        capabilities.setCapability("appium:udid", "emulator-5554");
////        capabilities.setCapability("appium:app", System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"ApiDemos-debug.apk");
//        capabilities.setCapability("appium:appPackage", "com.saucelabs.mydemoapp.rn");
//        capabilities.setCapability("appium:appActivity", "com.saucelabs.mydemoapp.rn.MainActivity");
//
//        URL url = new URL("http://0.0.0.0:4723");
//
//        driver = new AndroidDriver(url, capabilities);
//        System.out.println(driver.getSessionId());
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//    }
//
//    @AfterClass
//    public void AfterClassHook()
//    {
//        driver.quit();
//    }
//}
