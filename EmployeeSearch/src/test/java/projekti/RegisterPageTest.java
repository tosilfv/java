package projekti;

import org.apache.commons.lang3.RandomStringUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.junit.FluentTest;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegisterPageTest extends FluentTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private Integer port;

    // register.html - TESTS
    // Testing that links and buttons work
    @Test
    public void clickRegisterSignupButtonThenPageSourceContainsSignUp() {
        clickRegisterSignupButton();
    }

    public void clickRegisterSignupButton() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        $("#signup").click();
        assertThat(pageSource()).contains("Sign up");
    }

    @Test
    public void clickToTermsOfServicePageLinkThenPageSourceContainsTermsOfServiceText() {
        clickToTermsOfServicePageLink();
    }

    public void clickToTermsOfServicePageLink() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        $("#toTermsOfServicePage").click();
        assertThat(pageSource()).contains("or the ability of any other person to access");
    }

    @Test
    public void clickRegisterBackToMainPageLinkThenPageSourceContainsPromotionText() {
        clickRegisterBackToMainPageLink();
    }

    public void clickRegisterBackToMainPageLink() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        $("#backToMainPage").click();
        assertThat(pageSource()).contains("documents, profile avatar and more");
    }

    // Testing that sign up form works
    @Test
    public void clickRegisterErrorBackToRegisterPageLinkThenPageSourceContainsSignUp() {
        clickRegisterErrorBackToRegisterPageLink();
    }

    public void clickRegisterErrorBackToRegisterPageLink() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = RandomStringUtils.randomAscii(20) + RandomStringUtils.randomGraph(20) + RandomStringUtils.randomPrint(20);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa" + RandomStringUtils.randomAlphabetic(24);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("Somehow registering for user");
        assertThat(pageSource()).contains("Some of the entered characters were");
        $("#backToRegisterPage").click();
        assertThat(pageSource()).contains("Sign up");
    }

    @Test
    public void canAddAccountWithShortInputs() {
        addAccountWithShortInputs();
    }

    public void addAccountWithShortInputs() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "BB";
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(4);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Si";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "to";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("Registering was successful for alias");
        assertThat(pageSource()).contains("to");
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM Account")
                .stream().filter(s -> s.values().stream().filter(t -> t.equals(userName))
                .count() > 0).count());
    }

    @Test
    public void canAddAccountWithLongInputs() {
        addAccountWithLongInputs();
    }

    public void addAccountWithLongInputs() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa" + RandomStringUtils.randomAlphabetic(24);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("Registering was successful for alias");
        assertThat(pageSource()).contains("tontsa" + aliAs.substring(6));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM Account")
                .stream().filter(s -> s.values().stream().filter(t -> t.equals(userName))
                .count() > 0).count());
    }

    @Test
    public void fieldsSignUpCannotBeEmptyFail() {
        signUpCannotBeEmptyFail();
    }

    public void signUpCannotBeEmptyFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        $("#signup").click();
        assertThat(pageSource()).contains("must not be empty");
        assertThat(pageSource()).contains("size must be between 2 and 60");
        assertThat(pageSource()).contains("size must be between 8 and 60");
        assertThat(pageSource()).contains("size must be between 6 and 30");
        assertThat(pageSource()).contains("size must be between 2 and 30");
    }

    @Test
    public void tooShortUsernameFail() {
        shortUsernameFail();
    }

    public void shortUsernameFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "E";
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("size must be between 2 and 60");
    }

    @Test
    public void tooLongUsernameFail() {
        longUsernameFail();
    }

    public void longUsernameFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphabetic(57);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("size must be between 2 and 60");
    }

    @Test
    public void fieldUsernameCannotBeEmptyFail() {
        usernameCannotBeEmptyFail();
    }

    public void usernameCannotBeEmptyFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("must not be empty");
        assertThat(pageSource()).contains("size must be between 2 and 60");
    }

    @Test
    public void fieldUsernameNotAllowedCharactersError() {
        usernameNotAllowedCharactersError();
    }

    public void usernameNotAllowedCharactersError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = RandomStringUtils.randomAscii(20) + RandomStringUtils.randomGraph(20) + RandomStringUtils.randomPrint(20);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa" + RandomStringUtils.randomAlphabetic(24);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("was not completed!");
        assertThat(pageSource()).contains("Some of the entered characters were");
    }

    @Test
    public void enteredUsernameIsAlreadyInUseError() {
        usernameIsAlreadyInUseError();
    }

    public void usernameIsAlreadyInUseError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "BB";
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(4);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Si";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "to";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        $("input[name=username]").fill().with(userName);
        $("input[name=password]").fill().with(passWord);
        $("input[name=confirm]").fill().with(conFirm);
        $("input[name=realname]").fill().with(realName);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("Somehow registering for user");
        assertThat(pageSource()).contains("BB");
        assertThat(pageSource()).contains("in use.");
    }

    @Test
    public void tooShortPasswordFail() {
        shortPasswordFail();
    }

    public void shortPasswordFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(3);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("size must be between 8 and 60");
        assertThat(pageSource()).contains("Password must be 8 or more characters in length.");
    }

    @Test
    public void tooLongPasswordFail() {
        longPasswordFail();
    }

    public void longPasswordFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(57);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("size must be between 8 and 60");
        assertThat(pageSource()).contains("Password must be no more than 60 characters in length.");
    }

    @Test
    public void fieldPasswordCannotBeEmptyFail() {
        passwordCannotBeEmptyFail();
    }

    public void passwordCannotBeEmptyFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        assertThat(pageSource()).contains("confirm must match password");
    }

    @Test
    public void fieldPasswordNotAllowedCharactersError() {
        passwordNotAllowedCharactersError();
    }

    public void passwordNotAllowedCharactersError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = RandomStringUtils.randomAscii(20) + RandomStringUtils.randomGraph(20) + RandomStringUtils.randomPrint(20);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa" + RandomStringUtils.randomAlphabetic(24);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        boolean passwordContainsWhiteSpace = returnTrueIfStringContainsWhiteSpaceCharacter(passWord);
        if (passwordContainsWhiteSpace == true) {
            assertThat(pageSource()).contains("Password contains a whitespace character.");
        } else {
            assertThat(pageSource()).contains("was not completed!");
            assertThat(pageSource()).contains("Some of the entered characters were");
        }
    }

    public boolean returnTrueIfStringContainsWhiteSpaceCharacter(String password) {
        boolean passwordContainsWhiteSpace = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if ((int) c == 9
                    || (int) c == 10
                    || (int) c == 11
                    || (int) c == 12
                    || (int) c == 13
                    || (int) c == 32
                    || (int) c == 133
                    || (int) c == 160
                    || (int) c == 5760
                    || (int) c == 8192
                    || (int) c == 8193
                    || (int) c == 8194
                    || (int) c == 8195
                    || (int) c == 8196
                    || (int) c == 8197
                    || (int) c == 8198
                    || (int) c == 8199
                    || (int) c == 8200
                    || (int) c == 8201
                    || (int) c == 8202
                    || (int) c == 8232
                    || (int) c == 8233
                    || (int) c == 8239
                    || (int) c == 8287
                    || (int) c == 12288
                    || (int) c == 6158
                    || (int) c == 8203
                    || (int) c == 8204
                    || (int) c == 8205
                    || (int) c == 8288
                    || (int) c == 65279) {
                passwordContainsWhiteSpace = true;
                break;
            }
        }
        return passwordContainsWhiteSpace;
    }

    @Test
    public void tooShortConfirmFail() {
        shortConfirmFail();
    }

    public void shortConfirmFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(4);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm.substring(1));
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        assertThat(pageSource()).contains("confirm must match password");
    }

    @Test
    public void tooLongConfirmFail() {
        longConfirmFail();
    }

    public void longConfirmFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(4);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm + "0");
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        assertThat(pageSource()).contains("confirm must match password");
    }

    @Test
    public void fieldConfirmCannotBeEmptyFail() {
        confirmCannotBeEmptyFail();
    }

    public void confirmCannotBeEmptyFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(4);
        $("input[name=password]").fill().with(passWord);
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        assertThat(pageSource()).contains("confirm must match password");
    }

    @Test
    public void enteredShortConfirmMustMatchPasswordError() {
        shortConfirmMustMatchPasswordError();
    }

    public void shortConfirmMustMatchPasswordError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm.substring(1));
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa" + RandomStringUtils.randomAlphabetic(24);
        $("input[name=useralias]").fill().with(aliAs);
        $("#_hiddensignup").click();
        assertThat(pageSource()).contains("The entered Confirm does not match");
    }

    @Test
    public void enteredLongConfirmMustMatchPasswordError() {
        longConfirmMustMatchPasswordError();
    }

    public void longConfirmMustMatchPasswordError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(55);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm + "X");
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa" + RandomStringUtils.randomAlphabetic(24);
        $("input[name=useralias]").fill().with(aliAs);
        $("#_hiddensignup").click();
        assertThat(pageSource()).contains("The entered Confirm does not match");
    }

    @Test
    public void enteredWrongConfirmMustMatchPasswordError() {
        wrongConfirmMustMatchPasswordError();
    }

    public void wrongConfirmMustMatchPasswordError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = RandomStringUtils.randomAscii(20) + RandomStringUtils.randomGraph(20) + RandomStringUtils.randomPrint(20);
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa" + RandomStringUtils.randomAlphabetic(24);
        $("input[name=useralias]").fill().with(aliAs);
        $("#_hiddensignup").click();
        assertThat(pageSource()).contains("The entered Confirm does not match");
    }

    @Test
    public void tooShortRealnameFail() {
        shortRealnameFail();
    }

    public void shortRealnameFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni ";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("size must be between 6 and 30");
    }

    @Test
    public void tooLongRealnameFail() {
        longRealnameFail();
    }

    public void longRealnameFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(19);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa" + RandomStringUtils.randomAlphabetic(24);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("size must be between 6 and 30");
    }

    @Test
    public void fieldRealnameCannotBeEmptyFail() {
        realnameCannotBeEmptyFail();
    }

    public void realnameCannotBeEmptyFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String aliAs = "tontsa";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("must not be empty");
        assertThat(pageSource()).contains("size must be between 6 and 30");
    }

    @Test
    public void fieldRealnameNotAllowedCharactersError() {
        realnameNotAllowedCharactersError();
    }

    public void realnameNotAllowedCharactersError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = RandomStringUtils.randomAscii(10) + RandomStringUtils.randomGraph(10) + RandomStringUtils.randomPrint(10);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa" + RandomStringUtils.randomAlphabetic(24);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("was not completed!");
        assertThat(pageSource()).contains("Some of the entered characters were");
    }

    @Test
    public void tooShortAliasFail() {
        shortAliasFail();
    }

    public void shortAliasFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "t";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("size must be between 2 and 30");
    }

    @Test
    public void tooLongAliasFail() {
        longAliasFail();
    }

    public void longAliasFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = "tontsa" + RandomStringUtils.randomAlphabetic(25);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("size must be between 2 and 30");
    }

    @Test
    public void fieldAliasCannotBeEmptyFail() {
        aliasCannotBeEmptyFail();
    }

    public void aliasCannotBeEmptyFail() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver";
        $("input[name=realname]").fill().with(realName);
        $("#signup").click();
        assertThat(pageSource()).contains("must not be empty");
        assertThat(pageSource()).contains("size must be between 2 and 30");
    }

    @Test
    public void fieldAliasNotAllowedCharactersError() {
        aliasNotAllowedCharactersError();
    }

    public void aliasNotAllowedCharactersError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "EE_@" + RandomStringUtils.randomAlphanumeric(56);
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(56);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Silfver" + RandomStringUtils.randomAlphabetic(18);
        $("input[name=realname]").fill().with(realName);
        String aliAs = RandomStringUtils.randomAscii(10) + RandomStringUtils.randomGraph(10) + RandomStringUtils.randomPrint(10);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("was not completed!");
        assertThat(pageSource()).contains("Some of the entered characters were");
    }

    @Test
    public void enteredAliasIsAlreadyInUseError() {
        aliasIsAlreadyInUseError();
    }

    public void aliasIsAlreadyInUseError() {
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        String userName = "BB";
        $("input[name=username]").fill().with(userName);
        String passWord = "aZ0&" + RandomStringUtils.randomAlphabetic(4);
        $("input[name=password]").fill().with(passWord);
        String conFirm = passWord;
        $("input[name=confirm]").fill().with(conFirm);
        String realName = "Toni Si";
        $("input[name=realname]").fill().with(realName);
        String aliAs = "to";
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        goTo("http://localhost:" + port + "/EmployeeSearch/Register");
        userName = "ER";
        $("input[name=username]").fill().with(userName);
        $("input[name=password]").fill().with(passWord);
        $("input[name=confirm]").fill().with(conFirm);
        $("input[name=realname]").fill().with(realName);
        $("input[name=useralias]").fill().with(aliAs);
        $("#signup").click();
        assertThat(pageSource()).contains("Somehow registering for user");
        assertThat(pageSource()).contains("to");
        assertThat(pageSource()).contains("in use.");
    }
}
