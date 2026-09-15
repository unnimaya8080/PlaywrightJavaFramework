package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Route_fulfill {

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

    @Test(description = "creating new events in the event hub")
    public void createEventTest() {

        page.getByPlaceholder("you@email.com").fill("unnimaya8080@gmail.com");
        page.getByLabel("Password").fill("Qwerty@123");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();


        page.route("**/api/events**",route -> route.fulfill(new Route.FulfillOptions()
                .setPath(Paths.get("src/test/resources/events_6.json"))));
        page.navigate("https://eventhub.rahulshettyacademy.com/events");
        page.waitForTimeout(7000);

        Locator eventCards = page.getByTestId("event-card"); //get the common clocator of event cards
        //Playwright assertion-wait for the element to be visible
        assertThat(eventCards.first()).isVisible();  //Wait for the first card to load

        //TestNG assertion--doesn't wait for element to be visible
        Assert.assertEquals(eventCards.count(),6); //Check whether the count of cards are 6

        //Check the banner of the warning message is displayed
        Locator bannerMessage=page.locator("xpath=//span[contains(text(),'Your sandbox holds up to ')]");
        assertThat(bannerMessage).isVisible();

        /*Now change the events count to 4, then the banner should be hidden
        For that use the same code from page.route till the assertion- just create another json file in the same
        path keep only 4 events in the response body
        also change the event count to 4 and in the assertion related to banner instead of isVisible() use isHidden
        */



        page.route("**/api/events**",route -> route.fulfill(new Route.FulfillOptions()
                .setPath(Paths.get("src/test/resources/events_4.json"))));
        page.navigate("https://eventhub.rahulshettyacademy.com/events");
        page.waitForTimeout(7000);

        Locator eventCards1 = page.getByTestId("event-card"); //get the common clocator of event cards
        //Playwright assertion-wait for the element to be visible
        assertThat(eventCards1.first()).isVisible();  //Wait for the first card to load

        //TestNG assertion--doesn't wait for element to be visible
        Assert.assertEquals(eventCards1.count(),4); //Check whether the count of cards are 6

        //Check the banner of the warning message is displayed
        Locator bannerMessage1=page.locator("xpath=//span[contains(text(),'Your sandbox holds up to ')]");
        assertThat(bannerMessage1).isHidden();










    }
}