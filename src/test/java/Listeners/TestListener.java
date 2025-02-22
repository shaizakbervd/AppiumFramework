package Listeners;

import BaseClass.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class TestListener implements ITestListener {


    public void onTestFailure(ITestResult result)
    {
        if (result.getThrowable() !=null)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            result.getThrowable().printStackTrace();
            System.out.println(sw.toString());
        }

        BaseTest baseTest = new BaseTest();
        File file = baseTest.getDriver().getScreenshotAs(OutputType.FILE);

        //getting the parameter such as platformname, devicename, udid from testng.xml file
        Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String imagePath = "Screenshots"+File.separator+params.get("platformName")+"_"+params.get("deviceName")+"_"+params.get("udid")+File.separator+

                baseTest.getDateTime()+File.separator+
                //we got the test class name like logintests ya phr producttests and then the method name where it failed tu ye pora screenshot ka naam banega which will be unique
                result.getTestClass().getRealClass().getSimpleName()+File.separator+result.getName()+".png";
        String completeImagePath = System.getProperty("user.dir")+File.separator+imagePath;
        try {

            //this creates screenshot in root directors
            FileUtils.copyFile(file, new File(imagePath));
            //testng ki default report ma image ka path dal rhy ha
            Reporter.log("<a href='"+completeImagePath+"'><img src='"+completeImagePath+"' height='100' width='100'/></a>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
