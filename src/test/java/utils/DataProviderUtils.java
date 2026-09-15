package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProviderUtils {

    public static Object[][] getData(String filePath) throws Exception {

        Gson gson = new Gson();

        Type type = new TypeToken<List<HashMap<String, String>>>() {}.getType();

        List<HashMap<String, String>> data =
                gson.fromJson(new FileReader(filePath), type);

        Object[][] testData = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i);
        }

        return testData;
    }
}