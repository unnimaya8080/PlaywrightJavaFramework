package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    Playwright playwright;
    Browser browser;
    Page page;
    String base_url;


    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {

        //Properties- a java class thet helps to access the contents in properties file in key value pairs
        Properties prop =new Properties();
        //FileInputStream - to read the data in the give file (path)
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        prop.load(fis);

        //To get the value of the browser
        //get the browser from runtime via maven command (-Dbrowser=xxx) if is not null execute the browser passed through maven command otherwise use the one from config.properties
        String browserName =System.getProperty("browser")!=null?System.getProperty("browser"):prop.getProperty("browser");
//        String browserName= prop.getProperty("browser");


        playwright = Playwright.create();

        //code for invoking browser
        if("firefox".equals(browserName))
        {
            browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }
        else if ("safari".equals(browserName))
        {
            browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }
        else
        {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }


        page = browser.newPage();
//setting the environment from maven commands in run time
        String envName=System.getProperty("env")!=null?System.getProperty("env"): prop.getProperty("env");
        base_url= prop.getProperty(envName+".base_url");

       // page.navigate("https://eventhub.rahulshettyacademy.com/login"); ----this need to be given inside the test
        PlaywrightAssertions.setDefaultAssertionTimeout(7000);
        page.setDefaultTimeout(9000);
    }
}
