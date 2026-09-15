package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {
    static Page page;
    String base_url;
    //Locators
    private static final String loginEmailPlaceholder="you@email.com";
    private static final String loginPassword="Password";




    public LoginPage(Page page, String base_url) //Constructor with the attributes passed from test class
    {
        this.page=page;
        this.base_url=base_url;

    }


        public DashboardPage loginToApplication()
        {
            page.navigate(base_url);
            assertThat(page).hasTitle("EventHub — Discover & Book Events");
            page.getByPlaceholder(loginEmailPlaceholder).fill("unnimaya8080@gmail.com");
            page.getByLabel(loginPassword).fill("Qwerty@123");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
            DashboardPage dashboardPage=new DashboardPage(page);
            return dashboardPage;
        }

}
