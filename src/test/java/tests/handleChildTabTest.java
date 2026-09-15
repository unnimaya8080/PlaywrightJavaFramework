package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class handleChildTabTest {

    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;

    @BeforeMethod(alwaysRun = true)
    public void setUp()
    {
        playwright=Playwright.create();
        browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context= browser.newContext();
        page= context.newPage();
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");

    }

    @Test(groups = {"smoke"})
    public void childWindowHandle()
    {
        //Locator of the blinking links
        Locator BlinkingText=page.locator(".blinkingText");
        //opening new child window
        Page childPage=context.waitForPage(()->BlinkingText.first().click());


        //since we disn't used page.navigate to get the child page we have to give the wait--wait for load state
        childPage.waitForLoadState();

        //getting the string of email id and pasting it in the email field of login page

        //String childText=childPage.locator("xpath=//a[contains(text(),'.com')]").innerText();
        String childText=childPage.locator(".red").textContent();
        String email=childText.split("at ")[1].split(" ")[0];


        //fill the username field of login page with the email id
        page.getByLabel("Username").fill(email);

        //To get the value entered in any input field use inputValue() method

        System.out.println(page.getByLabel("Username").inputValue());
        page.waitForTimeout(6000);




    }


}
