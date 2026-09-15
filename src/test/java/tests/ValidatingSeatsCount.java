package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ValidatingSeatsCount extends TestBase{



    @Test(description = "creating new events in the event hub")
    public void createEventTest() {
        String eventTitle= "Playwright test event";

        //LOGIN
        LoginPage loginPage =new LoginPage(page,base_url); //create login object

        //saving the DashboardPage object returned from LoginPage class to the variable 'dashboardPage'
        DashboardPage dashboardPage=loginPage.loginToApplication();

        //calling the menthod inside the DashboardPage class
        dashboardPage.validateDashboardTitle();

        //creating object of Navbar class
        Navbar navobj=new Navbar(page); //page is passed to the constructor in Navbar class
        navobj.ManageEvents();//calling the function in Navbar class

        //Object for craete events page
        CreateEventsPage createEventsPage=new CreateEventsPage(page);
        createEventsPage.createEvents(
                eventTitle,
                "Description of the playwright event",
                "Workshop",
                "Test city",
                "Test venue",
                "2026-09-20T12:19",
                "40",
                "300"
        );
        //Now create objects for events page and call methods

        //calling the  methods in EventsPage


        EventsPage eventsPage =new EventsPage(page);
        eventsPage.goToEvents();
     //   eventsPage.waitForEventsToLoad(); No need to call this since in the EventsPage we are calling it
        eventsPage.findEventCard(eventTitle);
        ///calling the getSeatCount() fun that returns the count of the seats before booking
        int seatCountBefore=eventsPage.getSeatCount(eventTitle);

        //calling the performBooking fun
       BookingPage bookingPage= eventsPage.performBooking(eventTitle);











        //The booking page
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("+")).click();
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("+")).click();
        page.locator("#customerName").fill("John Smith");
        page.locator("#customer-email").fill("abc@gmail.com");
        page.locator("#phone").fill("+4487654321");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirm Booking")).click();
        page.waitForTimeout(6000);

        //Validating booking confirmation
        assertThat(page.locator("xpath=//h3[contains(text(),'Booking Confirmed!')]")).isVisible();
        String bookingRef=page.locator(".booking-ref").innerText();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View My Bookings")).click();
        page.waitForTimeout(6000);

        //Validating the booking ref in the My bookings page

        //First find the common locator of the the cards






       Locator bookingCards= page.locator(".booking-ref");

       //filter the desired card from the cards that has the booking ref text
       Locator targetBookingCard= bookingCards.filter(new Locator.FilterOptions().setHasText(bookingRef));  //we already saved the booking ref to the variable bookingRef

        //now checking is the booking ref is visible in that particular card
        assertThat(targetBookingCard).isVisible();

        //Now checking the reduction of seats count after booking

        //naviagte to events page
        page.locator("#nav-events").click();
        page.waitForTimeout(8000);

        //Getting all the event cards
        Locator eventCards_AfterBooking = page.getByTestId("event-card");

        //Filtering the desired card that has the text "Playwright event"
        Locator targetCard_AfterBooking = eventCards_AfterBooking.filter(new Locator.FilterOptions().setHasText("Playwright event"));









        //Now to validate the seats count after booking
        String seatsAfterBook=targetCard_AfterBooking.getByText("seats").innerText();
        System.out.println(seatsAfterBook); //This will return String like--- 300 seats available

        //Now we need to get the cout of the seats from the string and convert it from string to integer
        //.split()[0] will split the string into an array and return the first item in the array
        //Integer.parseInt() will covert the string into integer value whch we then save it in searCountAfter variable
        int seatCountAfter=Integer.parseInt(seatsAfterBook.split(" ")[0]);


        //Now validate if the seat count got reduced after bookg

        Assert.assertTrue(seatCountAfter<seatCountBefore);











    }
}
