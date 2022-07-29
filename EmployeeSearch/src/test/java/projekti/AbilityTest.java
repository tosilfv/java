package projekti;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.fluentlenium.adapter.junit.FluentTest;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.domain.Ability;
import projekti.domain.Account;
import projekti.logic.repository.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AbilityTest extends FluentTest {

    @Autowired
    private AccountRepository accountRepository;

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

    // layout_home_nonedit.html - TESTS
    // Testing that ability adding works
    @Test
    public void addAbility() {
        $("#addAbilityLink").click();
        find("#addAbilityTextBox").fill().with("Lisätään taito 1.");
        $("#addAbilityButton").click();
        assertThat(pageSource()).contains("My Home Page");
        assertThat(pageSource()).contains("Lisätään taito 1.");
        assertThat(pageSource()).contains("Praises:");
    }

    // layout_home_visiting.html - TESTS
    // Testing that praise adding works
    @Test
    public void addPraise() {
        $("#addAbilityLink").click();
        find("#addAbilityTextBox").fill().with("Lisätään taito 1.");
        $("#addAbilityButton").click();
        assertThat(pageSource()).contains("My Home Page");
        assertThat(pageSource()).contains("Lisätään taito 1.");
        assertThat(pageSource()).contains("Praises:");

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
        find("[name=addPraiseButton]").first().click();
        find("[name=praisetext]").first().fill().with("Hyvä taito 1!");
        find("[name=submitNewPraise]").first().click();

        assertThat(pageSource()).contains("Already praised!");
        assertThat(pageSource()).contains("tontsa2");
        assertThat(pageSource()).contains("Hyvä taito 1!");

        Account account = this.accountRepository.findByUseralias("tontsa");
        List<Ability> abilities = account.getAbilities();
        int praises = 0;
        Ability ability = abilities.get(0);
        praises = ability.getPraises();
        assertEquals(1, praises);
    }
}
