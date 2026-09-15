package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class TraceViewer {


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
            page.navigate("https://rahulshettyacademy.com/loginpagePractise/");

        }

        @AfterMethod
        public void tearDown()
        {
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("trace.zip")));
        }

        @Test
        public void childWindowHandle()
        {
            Locator BlinkingText=page.locator(".blinkingText");
            //opening new child window
            Page childPage=context.waitForPage(()->BlinkingText.first().click());
            childPage.waitForLoadState();
            String childText=childPage.locator(".red").textContent();
            String email=childText.split("at ")[1].split(" ")[0];
            page.getByLabel("Username").fill(email);

            System.out.println(page.getByLabel("Username").inputValue());
            page.waitForTimeout(6000);




        }


    }


