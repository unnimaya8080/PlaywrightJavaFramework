package tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class Data_from_jsonTest {

    //Dynamically read the data from json file and convert it into Hashmap object

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws Exception {

        Gson gson = new Gson();

        Type type = new TypeToken<List<HashMap<String, String>>>() {}.getType();

        List<HashMap<String, String>> data =
                gson.fromJson(
                        new FileReader("src/test/resources/TestDataDemo.json"),
                        type
                );

        Object[][] testData = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i);
        }

        return testData;
    }

    @Test(dataProvider = "loginData",groups = {"smoke"})
    public void loginTest(HashMap<String, String> data) {

        System.out.println("Email: " + data.get("email"));
        System.out.println("Password: " + data.get("password"));
    }
}
