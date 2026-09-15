package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderDemo {

    @DataProvider(name="loginTestData")  //DataProvider will return multi dimentional array of objects
    public Object[][] getLoginData() //return object
    {
        return new Object[][]{{"user1@gmail.com","passwrord1"},{"user2@gmail.com","passwrord2"}};  // we can give any number of value combinations
    }

    @Test (dataProvider = "loginTestData") //provide name of the data provider
    public void loginTest(String email,String password)  //Give the names of the parameters
    {
        System.out.println(email);
        System.out.println(password);
    }










}
