package projekti.logic.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.domain.Account;
import projekti.logic.repository.AccountRepository;
import projekti.logic.service.ConnectionsService;
import projekti.logic.service.HomeService;

@Controller
public class ConnectionsController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConnectionsService connectionsService;

    @Autowired
    private HomeService homeService;

    // LOGGED IN
    // GET-REQUESTS
    @Secured("USER")
    @GetMapping("/EmployeeSearch/Users/{useralias}/Connections")
    public String userConnections(Model model, @PathVariable String useralias) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            Account userAccount = this.accountRepository.findByUseralias(useralias);
            model.addAttribute("requestsSent", userAccount.getConnectionRequestsSent());
            return "connections";
        }
    }

    // LOGGED IN
    // POST-REQUESTS
    @Secured("USER")
    @PostMapping("/EmployeeSearch/Users/{useralias}/Connections/{visitingalias}/Accept")
    public String userConnectionsAccept(Model model, @PathVariable String useralias,
            @PathVariable String visitingalias) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            Account userAccount = this.accountRepository.findByUseralias(useralias);
            Account visitingAccount = this.accountRepository.findByUseralias(visitingalias);
            this.connectionsService.arrangeConnectionsEstablished(userAccount);
            this.connectionsService.arrangeConnectionsEstablished(visitingAccount);
            this.connectionsService.connectionRequestAccept(userAccount, visitingAccount);
            return "redirect:/EmployeeSearch/Users/" + useralias + "/Visiting/" + visitingalias;
        }
    }

    @Secured("USER")
    @PostMapping("/EmployeeSearch/Users/{useralias}/Connections/{visitingalias}/Cancel")
    public String userConnectionsCancel(Model model, @PathVariable String useralias,
            @PathVariable String visitingalias) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            Account userAccount = this.accountRepository.findByUseralias(useralias);
            Account visitingAccount = this.accountRepository.findByUseralias(visitingalias);
            this.connectionsService.connectionRequestCancel(userAccount, visitingAccount);
            return "redirect:/EmployeeSearch/Users/" + useralias + "/Visiting/" + visitingalias;
        }
    }

    @Secured("USER")
    @PostMapping("/EmployeeSearch/Users/{useralias}/Connections/{visitingalias}/Reject")
    public String userConnectionsReject(Model model, @PathVariable String useralias,
            @PathVariable String visitingalias) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            Account userAccount = this.accountRepository.findByUseralias(useralias);
            Account visitingAccount = this.accountRepository.findByUseralias(visitingalias);
            this.connectionsService.connectionRequestReject(userAccount, visitingAccount);
            return "redirect:/EmployeeSearch/Users/" + useralias + "/Visiting/" + visitingalias;
        }
    }

    @Secured("USER")
    @PostMapping("/EmployeeSearch/Users/{useralias}/Connections/{visitingalias}/Remove")
    public String userConnectionsRemove(Model model, @PathVariable String useralias,
            @PathVariable String visitingalias) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            Account userAccount = this.accountRepository.findByUseralias(useralias);
            Account visitingAccount = this.accountRepository.findByUseralias(visitingalias);
            this.connectionsService.connectionRemove(userAccount, visitingAccount);
            return "redirect:/EmployeeSearch/Users/" + useralias + "/Visiting/" + visitingalias;
        }
    }
}
