package Tests;

import BaseClass.BaseTest;
import BaseClass.MenuPage;
import Pages.LoginPage;
import Pages.ProductsPage;
import Pages.SettingsPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.*;
import java.lang.reflect.Method;

public class LoginTests extends BaseTest {

    LoginPage loginPage;
    SettingsPage settingsPage;
    MenuPage menupage;
    ProductsPage productsPage;
    String data;
    JSONObject logindata;

    @BeforeClass
    public void beforeclass() throws IOException {

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

    }

    @BeforeMethod
    public void beforemethod(Method m)
    {
        //bar bar har test ke liye initialize na krna pare isiliye hook ma initialize krdia
        loginPage = new LoginPage();
        System.out.println(m.getName());


    }

    @Test(priority = 1)
    public void InvalidUsername() {
//        settingsPage = new SettingsPage();
        menupage = new MenuPage();
        settingsPage = menupage.Click_SideMenu();
        settingsPage.Click_Login();

        loginPage.EnterUserName(logindata.getJSONObject("invalidUser").getString("username"));
        loginPage.EnterPassword(logindata.getJSONObject("invalidUser").getString("password"));
        loginPage.Click_Login();

        String expected_error = strings.get("err_invalid_usr_pwd");
        Assert.assertEquals(loginPage.Get_ErrorText(), expected_error);
        loginPage.ClearFields();

    }

    @Test(priority = 2)
    public void invalidPassword()
    {
        loginPage.EnterUserName(logindata.getJSONObject("invalidPassword").getString("username"));
        loginPage.EnterPassword(logindata.getJSONObject("invalidPassword").getString("password"));
        loginPage.Click_Login();

        String expected_error = strings.get("err_invalid_usr_pwd");
        Assert.assertEquals(loginPage.Get_ErrorText(), expected_error);
        loginPage.ClearFields();
    }

    @Test(priority = 3)
    public void ValidLogin()
    {

        loginPage.EnterUserName(logindata.getJSONObject("ValidCreds").getString("username"));
        loginPage.EnterPassword(logindata.getJSONObject("ValidCreds").getString("password"));
        //jese he login kroge tu productpage pr land hojaoge and we set the return type of productspage
        productsPage = loginPage.Click_Login();

        String expected_error = strings.get("products_title");
        Assert.assertEquals(productsPage.getTitle(), expected_error);
    }

    @AfterClass
    public void closure()
    {
        settingsPage = menupage.Click_SideMenu();
        settingsPage.Click_Logout();
        closeApp();
        launchApp();
    }


}
