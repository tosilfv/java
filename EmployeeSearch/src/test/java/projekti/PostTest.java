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
public class PostTest extends FluentTest {

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

        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName2 = "toni2";
        $("input[name=username]").fill().with(userName2);
        String passWord2 = "nY&2gfaskd6785vchkLKHGUJ&_@IBTI8ut_IV&Ri7@rii";
        $("input[name=password]").fill().with(passWord2);
        String conFirm2 = passWord2;
        $("input[name=confirm]").fill().with(conFirm2);
        String realName2 = "Toni Silfver2";
        $("input[name=realname]").fill().with(realName2);
        String useralias2 = "tontsa2";
        $("input[name=useralias]").fill().with(useralias2);
        $("#signup").click();

        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#LoginFormUsername").fill().with(userName);
        $("#LoginFormPassword").fill().with(passWord);
        $("#userLogin").click();
    }

    // posts.html and comments.html - TESTS
    // Testing that post adding works
    @Test
    public void addPost() {
        $("#navbarPostsButton").click();
        assertThat(pageSource()).contains("Posts");
        assertThat(pageSource()).contains("New Post Title:");
        assertThat(pageSource()).contains("New Post Message:");
        assertThat(pageSource()).contains("Recent Posts:");

        find("[name=title]").fill().with("otsikko 1");
        find("[name=message]").fill().with("sisältö 1");
        find("[name=sendPostButton]").click();
        assertThat(pageSource()).contains("Comments");
        assertThat(pageSource()).contains("otsikko 1");
        assertThat(pageSource()).contains("sisältö 1");
        assertThat(pageSource()).contains("New Comment:");
    }

    // Testing that comment adding works
    @Test
    public void addComment() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Users/tontsa/Visiting/tontsa2");
        find("[name=addConnectionButton]").click();

        $("#navbarPostsButton").click();
        assertThat(pageSource()).contains("Posts");
        assertThat(pageSource()).contains("New Post Title:");
        assertThat(pageSource()).contains("New Post Message:");
        assertThat(pageSource()).contains("Recent Posts:");

        find("[name=title]").fill().with("otsikko 1");
        find("[name=message]").fill().with("sisältö 1");
        find("[name=sendPostButton]").click();
        assertThat(pageSource()).contains("Comments");
        assertThat(pageSource()).contains("otsikko 1");
        assertThat(pageSource()).contains("sisältö 1");
        assertThat(pageSource()).contains("New Comment:");

        $("#navbarDropdownMenuLink").click();
        $("#signoutLink").click();
        assertThat(pageSource()).contains("Welcome, visitor!");
        assertThat(pageSource()).contains("Logged out");
        assertThat(pageSource()).contains("FREE!");
        assertThat(pageSource()).contains("I got hired!");

        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#LoginFormUsername").fill().with("toni2");
        $("#LoginFormPassword").fill().with("nY&2gfaskd6785vchkLKHGUJ&_@IBTI8ut_IV&Ri7@rii");
        $("#userLogin").click();

        goTo("http://localhost:" + port + "/EmployeeSearch/Users/tontsa2/Visiting/tontsa");
        find("[name=acceptConnectionButton]").click();

        $("#navbarPostsButton").click();
        assertThat(pageSource()).contains("Posts");
        assertThat(pageSource()).contains("New Post Title:");
        assertThat(pageSource()).contains("New Post Message:");
        assertThat(pageSource()).contains("Recent Posts:");

        find("[name=postTitleLink]").click();
        find("[name=response]").fill().with("kommentti 1");
        find("[name=addCommentButton]").click();

        assertThat(pageSource()).contains("Comments");
        assertThat(pageSource()).contains("kommentti 1");
        assertThat(pageSource()).contains("tontsa2");
    }
}
