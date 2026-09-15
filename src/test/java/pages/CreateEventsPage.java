package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CreateEventsPage {
    Page page;

    //Locators
    private static final String eventTitleCSS="#event-title-input";
    private static final String eventDescriptionXpath="xpath=//textarea[@placeholder='Describe the event…']";
    private static final String eventCategoryLabel="Category";
    private static final String eventCityCSS="#city";
    private static final String eventVenueCSS="#venue";
    private static final String eventDateLabel="Event Date & Time";
    private static final String eventPriceLabel="Price ($)";
    private static final String eventTotalSeatsCSS="#total-seats";
    private static final String eventSubmitBtnCSS="button[type='submit']";
    private static final String eventSuccessText="Event created!";

    //constructor
    public  CreateEventsPage(Page page)
    {
        this.page=page;
    }

    public void createEvents(String title, String description, String category,String city, String venue,
                             String date, String price, String totalseats)
    {

        page.locator(eventTitleCSS).fill(title);
        page.locator(eventDescriptionXpath).fill(description);
        page.getByLabel(eventCategoryLabel).selectOption(category);
        page.locator(eventCityCSS).fill(city, new Locator.FillOptions().setTimeout(11000));
        page.locator(eventVenueCSS).fill(venue);
        page.getByLabel(eventDateLabel).fill(date);
        page.getByLabel(eventPriceLabel).fill(price);
        page.locator(eventTotalSeatsCSS).fill(totalseats);
        page.locator(eventSubmitBtnCSS).click();
        page.waitForTimeout(3000);
        assertThat(page.getByText(eventSuccessText)).isVisible();
    }
}
