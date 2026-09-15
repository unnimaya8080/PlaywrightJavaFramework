package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.DataProviderUtils;

import java.util.HashMap;

public class E2ETestDataGriven extends TestBase{

    @DataProvider(name="e2EtestData")
    public Object[][] e2eData() throws Exception {
        return DataProviderUtils.getData("src/test/resources/E2ETestData.json");
    }



    @Test(dataProvider ="e2EtestData" )
    public void createEventTest(HashMap <String,String> data) {


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
                data.get("eventTitle"),
                data.get("description"),
               data.get("category"),
                data.get("city"),
                data.get("venue"),
                data.get("datetime"),
                data.get("price"),
                data.get("totalseats")


        );
        //Now create objects for events page and call methods

        //calling the  methods in EventsPage


        EventsPage eventsPage =new EventsPage(page);
        eventsPage.goToEvents();
     //   eventsPage.waitForEventsToLoad(); No need to call this since in the EventsPage we are calling it
        eventsPage.findEventCard(data.get("eventTitle"));
        ///calling the getSeatCount() fun that returns the count of the seats before booking
        int seatCountBefore=eventsPage.getSeatCount(data.get("eventTitle"));

        //calling the performBooking fun
       BookingPage bookingPage= eventsPage.performBooking(data.get("eventTitle"));
       bookingPage.fillAndConfirm(
               data.get("customerName"),
               data.get("customerEmail"),
               data.get("customerPhone"));











//        //Validating the booking ref in the My bookings page
//
//        //First find the common locator of the the cards
//       Locator bookingCards= page.locator(".booking-ref");
//
//       //filter the desired card from the cards that has the booking ref text
//       Locator targetBookingCard= bookingCards.filter(new Locator.FilterOptions().setHasText(bookingRef));  //we already saved the booking ref to the variable bookingRef
//
//        //now checking is the booking ref is visible in that particular card
//        assertThat(targetBookingCard).isVisible();
//
//        //Now checking the reduction of seats count after booking
//
//        //naviagte to events page
//        page.locator("#nav-events").click();
//        page.waitForTimeout(8000);
//
//        //Getting all the event cards
//        Locator eventCards_AfterBooking = page.getByTestId("event-card");
//
//        //Filtering the desired card that has the text "Playwright event"
//        Locator targetCard_AfterBooking = eventCards_AfterBooking.filter(new Locator.FilterOptions().setHasText("Playwright event"));
//
//
//
//
//
//
//
//
//
//        //Now to validate the seats count after booking
//        String seatsAfterBook=targetCard_AfterBooking.getByText("seats").innerText();
//        System.out.println(seatsAfterBook); //This will return String like--- 300 seats available
//
//        //Now we need to get the cout of the seats from the string and convert it from string to integer
//        //.split()[0] will split the string into an array and return the first item in the array
//        //Integer.parseInt() will covert the string into integer value whch we then save it in searCountAfter variable
//        int seatCountAfter=Integer.parseInt(seatsAfterBook.split(" ")[0]);
//
//
//        //Now validate if the seat count got reduced after bookg
//
//        Assert.assertTrue(seatCountAfter<seatCountBefore);











    }
}
