package projekti;

import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.domain.Account;
import projekti.logic.repository.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProfilePictureTest extends FluentTest {

    @Autowired
    private AccountRepository accountRepository;

    @LocalServerPort
    private Integer port;

    public Account getAccount() {
        return this.accountRepository.findByUseralias("tontsa");
    }

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

    // preferences.html and layout_home_nonedit.html - TESTS
    // Testing stock that profile picture adding works
    @Test
    public void clickNavbarPreferencesButtonAndSelectStockMaleProfilePicture() {
        $("#navbarPreferencesButton").click();
        $("#profilePictureStockSelector").click();
        $("#profilePictureStockMale").click();
        $("#selectStockProfilePictureButton").click();
        assertThat(getAccount().getStockProfilePicture()).contains("male");
    }

    @Test
    public void clickNavbarPreferencesButtonAndSelectStockFemaleProfilePicture() {
        $("#navbarPreferencesButton").click();
        $("#profilePictureStockSelector").click();
        $("#profilePictureStockFemale").click();
        $("#selectStockProfilePictureButton").click();
        assertThat(getAccount().getStockProfilePicture()).contains("female");
    }

    @Test
    public void clickNavbarPreferencesButtonAndSelectStockNeutralProfilePicture() {
        $("#navbarPreferencesButton").click();
        $("#profilePictureStockSelector").click();
        $("#profilePictureStockNeutral").click();
        $("#selectStockProfilePictureButton").click();
        assertThat(getAccount().getStockProfilePicture()).contains("neutral");
    }
}
