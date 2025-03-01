package BaseClass;

import Utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

//use this listener along with with html-config.xml and extent properties to generate default reports
//@Listeners(ExtentITestListenerClassAdapter.class)
public class BaseTest {

    public static String devicename;
    protected static AppiumDriver driver;
    protected static String dateTime;
    protected Properties properties;
    protected static HashMap<String, String> strings = new HashMap<String, String>();
    InputStream inputStream;
    InputStream stringxml;
    TestUtils utils = new TestUtils();
    //start appium server programmatically instead of starting it manually
    private static AppiumDriverLocalService server;

    @BeforeSuite
    public void beforeSuite()
    {
        server = getAppiumServerDefault();
        server.start();
        utils.log().info("starting server");

    }

    @AfterSuite
    public void afterSuite()
    {
        server.stop();
        utils.log().info("stopping server");
    }

    public AppiumDriverLocalService getAppiumServerDefault()
    {
        return AppiumDriverLocalService.buildDefaultService();
    }

    //if the above method does not work

    public AppiumDriverLocalService getAppiumServerCustom()
    {
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                //path where node is installed
                .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
                //path where appium server is installed
                .withAppiumJS(new File(""))
                .usingPort(4723)
                //if session exist then over ride existing session
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE));
    }

    public String getDeviceName()
    {
        return devicename;
    }



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


        dateTime = utils.getDateTime();
        try{
            devicename = deviceName;
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
            utils.log().info("started driver session with id "+driver.getSessionId());

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
