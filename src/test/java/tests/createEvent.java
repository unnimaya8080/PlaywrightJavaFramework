package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class createEvent {

//    declaring the variables in global level

    Playwright playwright;
    Browser browser;
    Page page;


    @BeforeMethod  // this will be executed before every tests within the class
    public void setUp()
    {
        playwright= Playwright.create();
        browser =playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page=browser.newPage();
        page.navigate("https://eventhub.rahulshettyacademy.com/login");
    }

    @Test (description = "creating new events in the event hub")
    public void createEventTest()
    {
        //Login steps
        //Page level assertion
        assertThat(page).hasTitle("EventHub — Discover & Book Events");
        page.getByPlaceholder("you@email.com").fill("unnimaya8080@gmail.com");
        page.getByLabel("Password").fill("Qwerty@123");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();

        //to validate if the user really logged in
        //Locator level assertion
        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Browse Events →"))).isVisible();

        //navigating to manage events page
        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");

        //Filling the form fields
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

        //To validate the success message
        assertThat(page.getByText("Event created!")).isVisible();

        //navigating to events page
        page.locator("#nav-events").click();

        //Validating the newly created event
        //Since all the event cards in the event page has same test id (event-card), we are fisrt assigning all these
        // card loacator a variable

        Locator eventCards= page.getByTestId("event-card");

        //Now fltering the desired card from these
        Locator requiredCard=eventCards.filter(new Locator.FilterOptions().setHasText("Playwright event"));

        //Now adding ssertion to check the card is visible
        assertThat(requiredCard).isVisible();





















    }
}
