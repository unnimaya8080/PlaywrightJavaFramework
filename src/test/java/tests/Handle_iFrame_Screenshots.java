package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class Handle_iFrame_Screenshots {



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
    public void iframeHandling()
    {
        //Locator of the iframe page
        FrameLocator iframePage = page.frameLocator("#courses-iframe");

        //Now to handle the components inside the iframe we will be using iframePage instead of page
        //To click on the link inside iframe
        iframePage.getByRole(AriaRole.LINK,new FrameLocator.GetByRoleOptions().setName("Learning paths")).click();


        //To validate a text inside the page that opens after clicking link

        FrameLocator TextCheck=iframePage.frameLocator(".inner-box h1");
        System.out.println(TextCheck);
    }

    @Test
    public void Screenshot()
    {
        //Page Level screenshot
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("pagescreenshot.png")).setFullPage(true));

        //Locator Level Screenshot
        //Locator of thr element
        Locator box=page.locator("#openwindow");
        box.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("locator.png")));
    }



}
