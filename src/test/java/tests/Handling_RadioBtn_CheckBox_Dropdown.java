package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

// Check the second @Test for the code

public class Handling_RadioBtn_CheckBox_Dropdown {

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

    @Test
    public void HandlingUIComponents()
    {
        //Use getByRole option if the type of the components is mentioned (ex: role=checkboc)

        //RadioBtn
        Locator UserRadioBtn=page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("User"));
        UserRadioBtn.click();
        //Handling the popup
        page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Okay")).click();
        //To check if it is really selected
        Assert.assertTrue(UserRadioBtn.isChecked());  //pass only if the condition is true

        //Checkbox
        Locator Checkbox=page.getByRole(AriaRole.CHECKBOX,new Page.GetByRoleOptions().setName("I Agree to the terms and conditions"));
        Checkbox.click();

        //Validation if it's checked
        Assert.assertTrue(Checkbox.isChecked());



        //Handling dropdown/combobox
        //Since the dropdown don't have label, here also we can use getByRole along with SelectOption
        page.getByRole(AriaRole.COMBOBOX).selectOption("Student");
        page.waitForTimeout(4000);



    }


}
