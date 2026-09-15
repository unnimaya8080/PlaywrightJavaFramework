package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class BookingPage {
    Page page;

    //LOCATORS
    private static final String customerNameCSS="#customerName";
    private static final String customerEmailCSS="#customer-email";
    private static final String customerPhoneCSS="#phone";
    private static final String BooingConfXpath ="xpath=//h3[contains(text(),'Booking Confirmed!')]";
    private static final String BooingRefCSS=".booking-ref";


    public BookingPage(Page page)
    {
        this.page=page;
    }
        //To avoid hardcode values in the method we are passing the values from test class while calling fn
    public void fillAndConfirm(String customerName, String customerEmail, String customerPhone)
    {
        //The booking page
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("+")).click();
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("+")).click();
        page.locator(customerNameCSS).fill(customerName);
        page.locator(customerEmailCSS).fill(customerEmail);
        page.locator(customerPhoneCSS).fill(customerPhone);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Booking")).click();
        page.waitForTimeout(6000);

        //Validating booking confirmation
        assertThat(page.locator(BooingConfXpath)).isVisible();
        String bookingRef=page.locator(BooingRefCSS).innerText();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View My Bookings")).click();
        page.waitForTimeout(6000);
    }

}
