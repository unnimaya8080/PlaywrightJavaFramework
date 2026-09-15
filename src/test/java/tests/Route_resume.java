package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Route_resume {

    Playwright playwright;
    Browser browser;
    Page page;


    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate("https://eventhub.rahulshettyacademy.com/login");

        //In order to imcrease the wait time of assertions beyond the defualt 5 seconds time
        PlaywrightAssertions.setDefaultAssertionTimeout(7000);
    }

    @Test(description = "trying to access someone else's booking")
    public void createEventTest() {

        page.getByPlaceholder("you@email.com").fill("unnimaya8080@gmail.com");
        page.getByLabel("Password").fill("Qwerty@123");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();

        page.locator("#nav-bookings").click();

        page.route("**/bookings**",route -> route.resume(new Route.ResumeOptions()
                .setUrl("https://api.eventhub.rahulshettyacademy.com/api/bookings/119672")));
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View Details")).click();
        assertThat(page.getByText("Unauthorized" )).isVisible();




    }


    }
