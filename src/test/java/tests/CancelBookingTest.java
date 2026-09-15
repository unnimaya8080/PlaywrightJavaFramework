package tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CancelBookingTest extends TestBase{

    @Test(description = "Cancel an existing booking and verify it's removed", groups = "framework")
    public void cancelBookingFlow() {
        page.screenshot();
        // Login
        LoginPage loginPage = new LoginPage(page, base_url);
        DashboardPage dashboardPage = loginPage.loginToApplication();
        dashboardPage.validateDashboardTitle();

        // Navigate to My Bookings
        page.locator("#nav-bookings").click();
        page.waitForTimeout(4000);

        // Ensure there is at least one booking and capture its booking reference
        Locator bookingCards = page.locator(".booking-ref");
        assertThat(bookingCards.first()).isVisible();
        String firstBookingRef = bookingCards.first().innerText();

        // View details of the first booking
        page.getByRole(AriaRole.BUTTON, new com.microsoft.playwright.Page.GetByRoleOptions().setName("View Details")).first().click();
        page.waitForTimeout(3000);

        // Verify bookings/* page is displayed
        Assert.assertTrue(page.url().contains("/bookings/"), "Expected bookings details URL to contain '/bookings/'");

        // Cancel the booking
        page.getByRole(AriaRole.BUTTON, new com.microsoft.playwright.Page.GetByRoleOptions().setName("Cancel Booking")).click();
        page.waitForTimeout(3000);

        // Go back to My Bookings and verify the booking ref no longer exists
        page.locator("#nav-bookings").click();
        page.waitForTimeout(4000);

        Locator removed = page.locator(".booking-ref").filter(new Locator.FilterOptions().setHasText(firstBookingRef));
        int count = removed.count();
        Assert.assertEquals(count, 0, "Expected booking record to be removed after cancellation");
    }
}
