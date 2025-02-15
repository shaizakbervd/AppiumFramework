package Tests;

import BaseClass.BaseTest;
import Pages.LoginPage;
import Pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class LoginTests extends BaseTest {

    LoginPage loginPage;
    ProductsPage productsPage;

    @BeforeClass
    public void beforeclass()
    {

    }

    @AfterClass
    public void afterclass()
    {

    }

    @BeforeMethod
    public void beforemethod(Method m)
    {
        //bar bar har test ke liye initialize na krna pare isiliye hook ma initialize krdia
        loginPage = new LoginPage();
        System.out.println(m.getName());

    }
    @AfterMethod
    public void aftermethod() {

    }

    @Test(priority = 1)
    public void InvalidUsername()
    {
        productsPage = new ProductsPage();
        productsPage.Click_Sidemenu();
        productsPage.Click_Login();

        loginPage.EnterUserName("hello@example.com");
        loginPage.EnterPassword("10203040");
        loginPage.Click_Login();

        String expected_error = "Provided credentials do not match any user in this service.";
        Assert.assertEquals(loginPage.Get_ErrorText(), expected_error);
        loginPage.ClearFields();
    }

    @Test(priority = 2)
    public void invalidPassword()
    {
        loginPage.EnterUserName("bob@example.com");
        loginPage.EnterPassword("10203043");
        loginPage.Click_Login();

        String expected_error = "Provided credentials do not match any user in this service.";
        Assert.assertEquals(loginPage.Get_ErrorText(), expected_error);
        loginPage.ClearFields();
    }

    @Test(priority = 3)
    public void ValidLogin()
    {

        loginPage.EnterUserName("bob@example.com");
        loginPage.EnterPassword("10203040");
        //jese he login kroge tu productpage pr land hojaoge and we set the return type of productspage
        productsPage = loginPage.Click_Login();

        String expected_error = "Products";
        Assert.assertEquals(productsPage.getTitle(), expected_error);
    }


}
