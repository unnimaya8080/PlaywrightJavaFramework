package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EventsPage {
    Page page;
    public EventsPage(Page page)
    {
        this.page=page;
    }
    ///---------------------------------------Go to EventsPage------------------------------
    //Navigate to evnts page
    public void goToEvents()
    {
        page.locator("#nav-events").click();
    }
    ///-----------------------------------------------waitForEventsToLoad-----------------------------------
    //validating events are loaded
    public Locator waitForEventsToLoad()
    {
        Locator eventCards = page.getByTestId("event-card"); //returns all the event cards
        assertThat(eventCards.first()).isVisible();//validate if the first event card is fully loaded
        return eventCards;
    }
    ///------------------------------------------getTargetCard--------------------------------------
    //Get the desired card
    public Locator findEventCard(String eventTitle)
    {
        Locator eventCards= waitForEventsToLoad();  //this will direclty call the method waitForEventsToLoad();
        Locator targetCard = eventCards.filter(new Locator.FilterOptions().setHasText(eventTitle));
        //if the raget card is visible then retuen it
        assertThat(targetCard).isVisible();
        return targetCard;
    }

    ///-----------------------------------------------getSeatCount--------------------------------
    public int getSeatCount(String eventTitle) //here argument is mentioned since we are aclling the finfEventCard fun
    {
        //assertThat(targetCard).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
        Locator targetCard=findEventCard(eventTitle); //to get the target card  by calling findEventCard fn and save it in variable targetCard

        //Now to validate the seats count first get the count before booking
        String seatsBeforeBook=targetCard.getByText("seats").innerText();
        System.out.println(seatsBeforeBook);

        //Now we need to get the cout of the seats from the string and convert it from string to integer
        //.split()[0] will split the string into an array and return the first item in the array
        //Integer.parseInt() will covert the string into integer value whch we then save it in searCountBefore variable
        int seatCountBefore=Integer.parseInt(seatsBeforeBook.split(" ")[0]);
        return seatCountBefore; ///return the seat count
    }
    /// ------------------------------------------ProceedToBooking--------------------------------
    ///
    /// @return

    public BookingPage performBooking(String eventTitle)
    {
        Locator targetCard=findEventCard(eventTitle);
        targetCard.getByTestId("book-now-btn").click();
        page.waitForTimeout(6000);
        return new BookingPage(page);

    }

}
