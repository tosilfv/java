package projekti;

import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NavbarTest extends FluentTest {

    @LocalServerPort
    private Integer port;

    @Before
    public void setUp() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "toni";
        $("input[name=username]").fill().with(userName);
        String passWord = "nY&2gfaskd6785vchkLKHGUJ&_@IBTI8ut_IV&Ri7@rii";
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String useralias = "tontsa";
        $("input[name=useralias]").fill().with(useralias);
        $("#signup").click();
        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#LoginFormUsername").fill().with(userName);
        $("#LoginFormPassword").fill().with(passWord);
        $("#userLogin").click();
    }

    // layout_navbar_loggedin.html - TESTS
    // Testing that links and buttons work
    @Test
    public void clickEmployeeSearchLink() {
        $("#employeeSearchLink").click();
        assertThat(pageSource()).contains("Home Page");
        assertThat(pageSource()).contains("Help");
        assertThat(pageSource()).contains("Terms of service");
        assertThat(pageSource()).contains("Sign out");
    }

    @Test
    public void clickNavbarSearchButton() {
        $("#navbarSearchButton").click();
        assertThat(pageSource()).contains("Search");
        assertThat(pageSource()).contains("Results For Names");
        assertThat(pageSource()).contains("Results For Aliases");
        assertThat(pageSource()).contains("No results.");
    }

    @Test
    public void clickNavbarConnectionsButton() {
        $("#navbarConnectionsButton").click();
        assertThat(pageSource()).contains("Connections");
        assertThat(pageSource()).contains("Requests Received From");
        assertThat(pageSource()).contains("Requests Sent To");
        assertThat(pageSource()).contains("Established Connections");
    }

    @Test
    public void clickNavbarPostsButton() {
        $("#navbarPostsButton").click();
        assertThat(pageSource()).contains("Posts");
        assertThat(pageSource()).contains("New Post Title:");
        assertThat(pageSource()).contains("New Post Message:");
        assertThat(pageSource()).contains("Recent Posts:");
    }

    @Test
    public void clickNavbarPreferencesButton() {
        $("#navbarPreferencesButton").click();
        assertThat(pageSource()).contains("What if I forget my Password? See Password on the");
        assertThat(pageSource()).contains("Current Profile Picture:");
        assertThat(pageSource()).contains("Want to change Alias? See Alias on the");
        assertThat(pageSource()).contains("CAUTION! This action is");
    }

    @Test
    public void clickNavbarUserlogoButton() {
        $("#navbarUserlogoButton").click();
        assertThat(pageSource()).contains("My Home Page");
        assertThat(pageSource()).contains("Toni Silfver");
        assertThat(pageSource()).contains("view");
        assertThat(pageSource()).contains("manage");
    }

    @Test
    public void clickNavbarDropdownMenuLinkAndMyHomePageLink() {
        $("#navbarDropdownMenuLink").click();
        $("#myHomePageLink").click();
        assertThat(pageSource()).contains("My Home Page");
        assertThat(pageSource()).contains("Toni Silfver");
        assertThat(pageSource()).contains("view");
        assertThat(pageSource()).contains("manage");
    }

    @Test
    public void clickNavbarDropdownMenuLinkAndSearchLink() {
        $("#navbarDropdownMenuLink").click();
        $("#searchLink").click();
        assertThat(pageSource()).contains("Search");
        assertThat(pageSource()).contains("Results For Names");
        assertThat(pageSource()).contains("Results For Aliases");
        assertThat(pageSource()).contains("No results.");
    }

    @Test
    public void clickNavbarDropdownMenuLinkAndConnectionsLink() {
        $("#navbarDropdownMenuLink").click();
        $("#connectionsLink").click();
        assertThat(pageSource()).contains("Connections");
        assertThat(pageSource()).contains("Requests Received From");
        assertThat(pageSource()).contains("Requests Sent To");
        assertThat(pageSource()).contains("Established Connections");
    }

    @Test
    public void clickNavbarDropdownMenuLinkAndUploadsLink() {
        $("#navbarDropdownMenuLink").click();
        $("#uploadsLink").click();
        assertThat(pageSource()).contains("Uploads");
        assertThat(pageSource()).contains("Files Uploaded");
        assertThat(pageSource()).contains("Filename");
        assertThat(pageSource()).contains("Upload File (MAX SIZE 2MB, LIMIT 10 FILES):");
    }

    @Test
    public void clickNavbarDropdownMenuLinkAndPostsLink() {
        $("#navbarDropdownMenuLink").click();
        $("#postsLink").click();
        assertThat(pageSource()).contains("Posts");
        assertThat(pageSource()).contains("New Post Title:");
        assertThat(pageSource()).contains("New Post Message:");
        assertThat(pageSource()).contains("Recent Posts:");
    }

    @Test
    public void clickNavbarDropdownMenuLinkAndPreferencesLink() {
        $("#navbarDropdownMenuLink").click();
        $("#preferencesLink").click();
        assertThat(pageSource()).contains("What if I forget my Password? See Password on the");
        assertThat(pageSource()).contains("Current Profile Picture:");
        assertThat(pageSource()).contains("Want to change Alias? See Alias on the");
        assertThat(pageSource()).contains("CAUTION! This action is");
    }

    @Test
    public void clickNavbarDropdownMenuLinkAndHelpLink() {
        $("#navbarDropdownMenuLink").click();
        $("#helpLink").click();
        assertThat(pageSource()).contains("Change Alias");
        assertThat(pageSource()).contains("Forgotten password");
        assertThat(pageSource()).contains("Deleting your Profile means that you");
        assertThat(pageSource()).contains("hides only your Home Page and all the contents");
    }

    @Test
    public void clickNavbarDropdownMenuLinkAndSignoutLink() {
        $("#navbarDropdownMenuLink").click();
        $("#signoutLink").click();
        assertThat(pageSource()).contains("Welcome, visitor!");
        assertThat(pageSource()).contains("Logged out");
        assertThat(pageSource()).contains("FREE!");
        assertThat(pageSource()).contains("I got hired!");
    }
}
