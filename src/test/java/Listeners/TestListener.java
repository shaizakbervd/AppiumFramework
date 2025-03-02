package Listeners;

import BaseClass.BaseTest;
import Reports.ExtentReport;
import Utils.TestUtils;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TestListener implements ITestListener {

    TestUtils utils = new TestUtils();


    public void onTestFailure(ITestResult result)
    {
        if (result.getThrowable() !=null)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            result.getThrowable().printStackTrace();
            utils.log().error(sw.toString());
//            System.out.println(sw.toString());
        }

        BaseTest baseTest = new BaseTest();
        File file = baseTest.getDriver().getScreenshotAs(OutputType.FILE);
        byte[] encoded = null;
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        try {
//            ExtentReport.getTest().fail("Test Failed: ",
//                    MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());

            ExtentReport.getTest().fail("Test Failed: ",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExtentReport.getTest().fail(result.getThrowable());



    }

    @Override
    public void onTestStart(ITestResult result)
    {
        BaseTest baseTest = new BaseTest();
        ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
                .assignCategory(baseTest.getDeviceName())
                .assignAuthor("Shaiz");

    }

    @Override
    public void onTestSuccess(ITestResult result)
    {
        ExtentReport.getTest().log(Status.PASS, "Test Passed");

    }

    @Override
    public void onTestSkipped(ITestResult result)
    {
        ExtentReport.getTest().log(Status.SKIP, "Test Skipped");


    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result)
    {

    }

    @Override
    public void onStart(ITestContext context)
    {

    }

    @Override
    public void onFinish(ITestContext context)
    {
        ExtentReport.getReporter().flush();
    }
}
