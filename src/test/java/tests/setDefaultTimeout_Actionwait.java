package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class setDefaultTimeout_Actionwait {
    Playwright playwright;
    Browser browser;
    Page page;


    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.navigate("https://eventhub.rahulshettyacademy.com/login");
        PlaywrightAssertions.setDefaultAssertionTimeout(7000);

        //now all actions will wait 9 secs rather than default 10s
        page.setDefaultTimeout(9000);
    }

    @Test(description = "creating new events in the event hub")
    public void createEventTest() {

        assertThat(page).hasTitle("EventHub — Discover & Book Events");
        page.getByPlaceholder("you@email.com").fill("unnimaya8080@gmail.com");
        page.getByLabel("Password").fill("Qwerty@123");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();
        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
        page.locator("#event-title-input").fill("Playwright event");
        page.locator("xpath=//textarea[@placeholder='Describe the event…']").fill("Description of the playwright event");
        page.getByLabel("Category").selectOption("Workshop");

        //in order to customise the wait fo the below fill action to 11sec
        page.locator("#city").fill("Test city", new Locator.FillOptions().setTimeout(11000));


        page.locator("#venue").fill("Test venue");
        page.getByLabel("Event Date & Time").fill("2026-08-20T12:19");
        page.getByLabel("Price ($)").fill("40");
        page.locator("#total-seats").fill("300");
        page.locator("button[type='submit']").click();
        page.waitForTimeout(3000);
        assertThat(page.getByText("Event created!")).isVisible();
        page.locator("#nav-events").click();
        Locator eventCards = page.getByTestId("event-card");
        Locator targetCard = eventCards.filter(new Locator.FilterOptions().setHasText("Playwright event"));
        assertThat(targetCard).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
    }
}
