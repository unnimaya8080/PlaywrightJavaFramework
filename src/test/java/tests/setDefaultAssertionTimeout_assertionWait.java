package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class setDefaultAssertionTimeout_assertionWait {
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

        assertThat(page).hasTitle("EventHub — Discover & Book Events");
        page.getByPlaceholder("you@email.com").fill("unnimaya8080@gmail.com");
        page.getByLabel("Password").fill("Qwerty@123");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();
        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
        page.locator("#event-title-input").fill("Playwright event");
        page.locator("xpath=//textarea[@placeholder='Describe the event…']").fill("Description of the playwright event");
        page.getByLabel("Category").selectOption("Workshop");
        page.locator("#city").fill("Test city");
        page.locator("#venue").fill("Test venue");
        page.getByLabel("Event Date & Time").fill("2026-08-20T12:19");
        page.getByLabel("Price ($)").fill("40");
        page.locator("#total-seats").fill("300");
        page.locator("button[type='submit']").click();
        page.waitForTimeout(3000);
        assertThat(page.getByText("Event created!")).isVisible();
        page.locator("#nav-events").click();
        Locator eventCards = page.getByTestId("event-card");
        Locator requiredCard = eventCards.filter(new Locator.FilterOptions().setHasText("Playwright event"));
        assertThat(requiredCard).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
    }
}
