package projekti.logic.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.logic.service.HomeService;
import projekti.logic.utility.CustomDate;

@Controller
public class TermsController {

    @Autowired
    private HomeService homeService;

    @Autowired
    CustomDate date;

    // LOGGED OUT
    // GET-REQUESTS
    @GetMapping("/EmployeeSearch/TermsOfService")
    public String termsOfService(Model model) {
        if (this.homeService.helloUser(model, "notLoggedIn") == false) {
            return "fragments/layout_address_error";
        } else {
            model.addAttribute("date", this.date.date());
            return "terms_of_service";
        }
    }

    // LOGGED IN
    // GET-REQUESTS
    @Secured("USER")
    @GetMapping("/EmployeeSearch/Users/{useralias}/TermsOfService")
    public String userTermsOfService(Model model, @PathVariable String useralias) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            return "terms_of_service";
        }
    }
}
