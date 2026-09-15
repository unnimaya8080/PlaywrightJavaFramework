package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FirstTest {

    @Test
    public void Demo()
    {
        //Create playwright object
        Playwright playwright = Playwright.create();

        //chromium
       Browser browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //firefox
//        Browser browser=playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //webkit
//       Browser browser=playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));


        Page page=browser.newPage();
        page.navigate("https://eventhub.rahulshettyacademy.com/login");
        System.out.println(page.title());
        assertThat(page).hasTitle("EventHub — Discover & Book Events");
        page.getByPlaceholder("you@email.com").fill("unnimaya8080@gmail.com");
        page.getByLabel("Password").fill("Qwerty@123");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();








    }

}
