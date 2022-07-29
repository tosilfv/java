package projekti.logic.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projekti.logic.service.HomeService;
import projekti.logic.utility.CustomDate;

@Controller
public class LoginController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private CustomDate date;

    // LOGGED OUT
    // GET-REQUESTS
    @GetMapping("/EmployeeSearch/LoginError")
    public String loginError(Model model) {
        if (this.homeService.helloUser(model, "notLoggedIn") == false) {
            return "fragments/layout_address_error";
        } else {
            model.addAttribute("date", this.date.date());
            return "login_error";
        }
    }

    @GetMapping("/EmployeeSearch/Login")
    public String loginFill(Model model) {
        if (this.homeService.helloUser(model, "notLoggedIn") == false) {
            return "fragments/layout_address_error";
        } else {
            model.addAttribute("date", this.date.date());
            return "login";
        }
    }
}
