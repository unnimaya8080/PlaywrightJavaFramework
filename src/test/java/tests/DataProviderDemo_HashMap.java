package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

public class DataProviderDemo_HashMap {

    @DataProvider(name="hashmapData")
    public Object[][] hashmapTestData()
    {
        //First create HashMap object for user 1
        HashMap <String,String> user1=new HashMap<>();
        user1.put("email","uesr1@gmail.com");
        user1.put("password","password1");

        // create HashMap object for user 2
        HashMap <String,String> user2=new HashMap<>();
        user2.put("email","uesr2@gmail.com");
        user2.put("password","password2");

        //returning the hashmap objects
        return new Object[][] {{user1},{user2}};

    }

    @Test(dataProvider ="hashmapData")
    public void loginTest(HashMap<String,String> data)  //HashMap<String,String> is the data type and "data is the variable
    {
        System.out.println(data.get("email")); //mention the keys
        System.out.println(data.get("password"));
    }






}
