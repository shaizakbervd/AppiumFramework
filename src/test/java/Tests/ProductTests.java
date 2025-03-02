package Tests;

import BaseClass.BaseTest;
import BaseClass.MenuPage;
import Pages.LoginPage;
import Pages.ProductsDetailsPage;
import Pages.ProductsPage;
import Pages.SettingsPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.*;
import java.lang.reflect.Method;

public class ProductTests extends BaseTest {

    LoginPage loginPage;
    SettingsPage settingsPage;
    MenuPage menupage;
    ProductsPage productsPage;
    ProductsDetailsPage productsDetailsPage;
    String data;
    JSONObject logindata;

    @BeforeClass
    public void beforeclass() throws IOException, InterruptedException {

        String datafilename = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "login.json";

        // Read file content
        FileInputStream fis = new FileInputStream(datafilename);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader reader = new BufferedReader(isr);

        // Convert file content to JSON
        StringBuilder jsonContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonContent.append(line);
        }
        reader.close();
        JSONTokener jsonTokener = new JSONTokener(jsonContent.toString());
        logindata = new JSONObject(jsonTokener);
//        Thread.sleep(3000);


    }

    @BeforeMethod
    public void beforemethod(Method m)
    {
        //bar bar har test ke liye initialize na krna pare isiliye hook ma initialize krdia
        loginPage = new LoginPage();
        System.out.println(m.getName());

        //test cases independent rahe tu beforemthod ma close and launch app use kro taake har case independent chale

    }
    @AfterMethod
    public void aftermethod() {

    }

    @Test(priority = 1)
    public void ValidProductonPage()
    {
        SoftAssert sa = new SoftAssert();
        menupage = new MenuPage();
        settingsPage = menupage.Click_SideMenu();
        loginPage = settingsPage.Click_Login();

        loginPage.EnterUserName(logindata.getJSONObject("ValidCreds").getString("username"));
        loginPage.EnterPassword(logindata.getJSONObject("ValidCreds").getString("password"));
        productsPage = loginPage.Click_Login();

        String producttitle = productsPage.getProductTitle();
        sa.assertEquals(producttitle, strings.get("title"));
        sa.assertEquals(productsPage.getProducPrice(), strings.get("price"));
        sa.assertAll();

    }

    @Test(priority = 2)
    public void ValidProductDetailsonPage()
    {
        SoftAssert sa = new SoftAssert();
        productsDetailsPage = productsPage.Click_Product();
        sa.assertEquals(productsDetailsPage.getProducTitle(), strings.get("title"));
        sa.assertEquals(productsDetailsPage.getProductPrice(), strings.get("price"));
//        sa.assertEquals(productsDetailsPage.getProductDescription().contains(strings.get("description")), "Does not matches");

        sa.assertAll();
    }
}
