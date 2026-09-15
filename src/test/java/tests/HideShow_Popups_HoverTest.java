package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class HideShow_Popups_HoverTest {
    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;

    @BeforeMethod
    public void setUp()
    {
        playwright=Playwright.create();
        browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context= browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page= context.newPage();
        page.navigate("https://rahulshettyacademy.com/AutomationPractice/");

    }

    @AfterMethod
    public void tearDown()
    {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
    }


    @Test
    public void HideShow()
    {
        //Check whether the componet is present initially
        Assert.assertTrue(page.getByPlaceholder("Hide/Show Example").isVisible());

        //Click on hide button
        page.locator("#hide-textbox").click();


        //Check if the element is present after hiding it
        Assert.assertTrue(page.getByPlaceholder("Hide/Show Example").isHidden());
    }


    @Test
    public void AlertPopUp()
    {
        // Here just before the step of Alert button click (Which will opens the popup), we are adding the listener
        page.onDialog(dialog -> dialog.accept());
        page.locator("#alertbtn").click();
        page.waitForTimeout(3000);
    }

    @Test
    public void Hover()
    {
        page.locator("#mousehover").hover();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Top")).click();
        page.waitForTimeout(3000);

    }
}
