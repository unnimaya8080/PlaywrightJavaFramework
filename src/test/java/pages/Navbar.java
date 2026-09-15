package pages;

import com.microsoft.playwright.Page;

public class Navbar {
    Page page;

    public Navbar(Page page)
    {
        this.page=page;
    }

    public void ManageEvents()
    {
        page.navigate("https://eventhub.rahulshettyacademy.com/admin/events");
    }
}
