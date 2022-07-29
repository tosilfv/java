package projekti;

import org.apache.commons.lang3.RandomStringUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.junit.FluentTest;
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
public class LoginPageTest extends FluentTest {

    @LocalServerPort
    private Integer port;

    // login.html - TESTS
    // Testing that links and buttons work
    @Test
    public void clickLoginUserLoginButtonThenPageSourceContainsErrorText() {
        clickLoginUserLoginButton();
    }

    public void clickLoginUserLoginButton() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#userLogin").click();
        assertThat(pageSource()).contains("Somehow login was not completed!");
    }

    @Test
    public void clickLoginSignUpLinkThenPageSourceContainsSignUp() {
        clickLoginSignUpLink();
    }

    public void clickLoginSignUpLink() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#toSignupPage").click();
        assertThat(pageSource()).contains("Sign up");
    }

    @Test
    public void clickLoginBackToMainPageLinkThenPageSourceContainsPromotionText() {
        clickLoginBackToMainPageLink();
    }

    public void clickLoginBackToMainPageLink() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#backToMainPage").click();
        assertThat(pageSource()).contains("are you waiting for? Register Today, it's completely");
    }

    // Testing that User Login form works
    @Test
    public void canLoginWithCorrectUsernameAndCorrectPassword() {
        loginWithCorrectUsernameAndCorrectPassword();
    }

    public void loginWithCorrectUsernameAndCorrectPassword() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "AA_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "bR8@" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "toni" + RandomStringUtils.randomAlphabetic(26);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#LoginFormUsername").fill().with(userName);
        $("#LoginFormPassword").fill().with(passWord);
        $("#userLogin").click();
        assertThat(pageSource()).contains(aliAs);
        assertThat(pageSource()).contains(realName);
    }

    // Testing that Spring handles User Login form logging in validation with DevelopmentSecurityConfiguration.java 
    @Test
    public void enteredCorrectUsernameAndEmptyPasswordError() {
        correctUsernameAndEmptyPasswordError();
    }

    public void correctUsernameAndEmptyPasswordError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "AA_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "bR8@" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "toni" + RandomStringUtils.randomAlphabetic(26);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#LoginFormUsername").fill().with(userName);
        $("#userLogin").click();
        assertThat(pageSource()).contains("Somehow login was not completed!");
    }

    @Test
    public void enteredEmptyUsernameAndCorrectPasswordError() {
        emptyUsernameAndCorrectPasswordError();
    }

    public void emptyUsernameAndCorrectPasswordError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "AA_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "bR8@" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "toni" + RandomStringUtils.randomAlphabetic(26);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#LoginFormPassword").fill().with(passWord);
        $("#userLogin").click();
        assertThat(pageSource()).contains("Somehow login was not completed!");
    }

    @Test
    public void enteredCorrectUsernameAndWrongPasswordError() {
        correctUsernameAndWrongPasswordError();
    }

    public void correctUsernameAndWrongPasswordError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "AA_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "bR8@" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "toni" + RandomStringUtils.randomAlphabetic(26);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#LoginFormUsername").fill().with(userName);
        $("#LoginFormPassword").fill().with(passWord.substring(1));
        $("#userLogin").click();
        assertThat(pageSource()).contains("Somehow login was not completed!");
    }

    @Test
    public void enteredWrongUsernameAndCorrectPasswordError() {
        wrongUsernameAndCorrectPasswordError();
    }

    public void wrongUsernameAndCorrectPasswordError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "AA_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "bR8@" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "toni" + RandomStringUtils.randomAlphabetic(26);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#LoginFormUsername").fill().with(userName.substring(1));
        $("#LoginFormPassword").fill().with(passWord);
        $("#userLogin").click();
        assertThat(pageSource()).contains("Somehow login was not completed!");
    }

    @Test
    public void enteredWrongUsernameAndWrongPasswordError() {
        wrongUsernameAndWrongPasswordError();
    }

    public void wrongUsernameAndWrongPasswordError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "AA_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "bR8@" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "toni" + RandomStringUtils.randomAlphabetic(26);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        goTo("http://localhost:" + port + "/EmployeeSearch/Login");
        $("#LoginFormUsername").fill().with(userName.substring(10));
        $("#LoginFormPassword").fill().with(passWord.substring(10));
        $("#userLogin").click();
        assertThat(pageSource()).contains("Somehow login was not completed!");
    }
}
