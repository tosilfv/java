package projekti.logic.control;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.domain.Account;
import projekti.logic.service.HomeService;
import projekti.logic.service.RegisterService;
import projekti.logic.utility.CustomDate;

@Controller
public class RegisterController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private CustomDate date;

    // LOGGED OUT
    // GET-REQUESTS
    @GetMapping("/EmployeeSearch/Register")
    public String registerFill(Model model, @ModelAttribute Account account) {
        if (this.homeService.helloUser(model, "notLoggedIn") == false) {
            return "fragments/layout_address_error";
        } else {
            model.addAttribute("date", this.date.date());
            return "register";
        }
    }

    @Secured("USER")
    @GetMapping("/EmployeeSearch/Register/{useralias}")
    public String registerOk(Model model, @PathVariable String useralias) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            model.addAttribute("date", this.date.date());
            return "register_ok";
        }
    }

    // LOGGED OUT
    // POST-REQUESTS
    @PostMapping("/EmployeeSearch/Register")
    public String registerCheck(Model model,
            @Valid @ModelAttribute Account account,
            BindingResult bindingResult) {
        return this.registerService.registerCheck(model, account, bindingResult);
    }
}
