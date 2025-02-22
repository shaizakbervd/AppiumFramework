package BaseClass;

import Utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTest {

    protected static AppiumDriver driver;
    protected static String dateTime;
    protected Properties properties;
    protected static HashMap<String, String> strings = new HashMap<String, String>();
    InputStream inputStream;
    InputStream stringxml;
    TestUtils utils;

    @BeforeMethod
    public void beforeMethod()
    {
        ((CanRecordScreen)driver).startRecordingScreen();

    }

    @AfterMethod
    public void afterMethod(ITestResult result)
    {
        //record video which output will be string
        String media = ((CanRecordScreen)driver).stopRecordingScreen();
        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
        String dir = "Videos"+File.separator+params.get("platformName")+"_"+params.get("deviceName")+"_"+params.get("udid")+File.separator+
                dateTime+File.separator+
                result.getTestClass().getRealClass().getSimpleName();

        File videoDir = new File(dir);
        if(!videoDir.exists())
        {
            //creating directory
            videoDir.mkdirs();
        }

        try {
            //you create encoded base64 empty stream
            FileOutputStream stream = new FileOutputStream(videoDir +File.separator+result.getName()+".mp4");
            //writing stream in our file as the string of media was in base64 tu usko decode kia
            stream.write(Base64.decodeBase64(media));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Parameters({"platformName", "deviceName", "udid"})
    @BeforeTest
    public void BeforeTestHook(String platformName, String deviceName, String udid) throws Exception {

        utils = new TestUtils();
        dateTime = utils.getDateTime();
        try{
            properties = new Properties();
            String propertiesFilename = "config.properties";
            String xmlFilename = "strings.xml";
            inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFilename);
            stringxml = getClass().getClassLoader().getResourceAsStream(xmlFilename);
            strings = utils.parseStringXML(stringxml);

            properties.load(inputStream);

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", platformName);
            capabilities.setCapability("appium:automationName", properties.getProperty("androidAutomationName"));
            capabilities.setCapability("appium:deviceName", deviceName);
            capabilities.setCapability("appium:udid", udid);
            capabilities.setCapability("appium:app", System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"App"+File.separator+"saucedemo.apk");
            capabilities.setCapability("appium:appPackage", properties.getProperty("androidappPackage"));
            capabilities.setCapability("appium:appActivity", properties.getProperty("androidappActivity"));

            URL url = new URL(properties.getProperty("AppiumURL"));

            driver = new AndroidDriver(url, capabilities);
            System.out.println(driver.getSessionId());
        } catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }finally {
            if(inputStream!=null)
            {
                inputStream.close();
            }
            if(stringxml!=null)
            {
                stringxml.close();
            }
        }
    }

    public void waitForVisibility(WebElement element)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.Wait));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void Click(WebElement element)
    {
        waitForVisibility(element);
        element.click();
    }

    public void SendKeys(WebElement element, String value)
    {
        waitForVisibility(element);
        element.sendKeys(value);
    }

    public void Clear_Fields(WebElement username, WebElement pwd)
    {
        waitForVisibility(username);
        waitForVisibility(pwd);
        username.clear();
        pwd.clear();
    }

    public String  GetAttribute(WebElement element, String attribute)
    {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    public void closeApp()
    {
        ((InteractsWithApps) driver).terminateApp(properties.getProperty("androidappPackage"));
    }

    public void launchApp()
    {
        ((InteractsWithApps) driver).activateApp(properties.getProperty("androidappPackage"));

    }

    public AppiumDriver getDriver()
    {
        return driver;
    }

    public String getDateTime()
    {
        return dateTime;
    }

    public WebElement ScrolltoElement() {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().description(\"parent\"))"
                        + ".scrollIntoView(new UiSelector().description(\"child\"))"));
    }

//    @AfterTest
//    public void AfterTestHook()
//    {
//        driver.quit();
//    }
}
