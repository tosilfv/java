package projekti.logic.control;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Ability;
import projekti.domain.Account;
import projekti.domain.Praise;
import projekti.logic.repository.AbilityRepository;
import projekti.logic.repository.AccountRepository;
import projekti.logic.repository.PraiseRepository;
import projekti.logic.repository.ProfilePictureRepository;
import projekti.logic.service.AbilityService;
import projekti.logic.service.ConnectionsService;
import projekti.logic.service.HomeService;

@Controller
public class AbilityController {

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private AbilityService abilityService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ConnectionsService connectionsService;

    @Autowired
    private HomeService homeService;

    @Autowired
    private PraiseRepository praiseRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    // LOGGED IN
    // POST-REQUESTS
    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/Abilities", method = RequestMethod.POST)
    public String abilityNew(Model model, @Valid @ModelAttribute Ability ability,
            BindingResult bindingResult, @PathVariable String useralias,
            @RequestParam String abilitytext) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            if (bindingResult.hasErrors()) {
                return "preferences";
            }
            this.abilityService.newAbility(useralias, abilitytext);
            return "redirect:/EmployeeSearch/Users/" + useralias;
        }
    }

    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/Abilities/{abilityid}/Praise/{visitingalias}", method = RequestMethod.POST)
    public String praiseNew(Model model, @Valid @ModelAttribute Praise praise,
            BindingResult bindingResult, @PathVariable String useralias,
            @PathVariable Long abilityid, @PathVariable String visitingalias, @RequestParam String praisetext) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            Account visitingAccount = this.accountRepository.findByUseralias(visitingalias);
            if (bindingResult.hasErrors()) {
                Account userAccount = this.accountRepository.findByUseralias(useralias);
                model.addAttribute("abilityid", abilityid);
                model.addAttribute("connectionEstablished", false);
                if (this.connectionsService.connectionIsEstablished(userAccount, visitingAccount) == true) {
                    model.addAttribute("connectionEstablished", true);
                }
                model.addAttribute("connectionEstablishedVisited", visitingAccount.getConnectionsEstablished());
                model.addAttribute("connectionEstablishedVisitedSize", visitingAccount.getConnectionsEstablished().size());
                model.addAttribute("loadedProfilePicture", this.profilePictureRepository.findByUseralias(visitingalias));
                model.addAttribute("requestReceived", false);
                if (this.connectionsService.requestIsReceived(userAccount, visitingAccount) == true) {
                    model.addAttribute("requestReceived", true);
                }
                model.addAttribute("requestSent", false);
                if (this.connectionsService.requestIsSent(userAccount, visitingAccount) == true) {
                    model.addAttribute("requestSent", true);
                }
                model.addAttribute("submittedProfilePicture", visitingAccount.isSubmittedProfilePicture());
                model.addAttribute("viewAllPraises", this.praiseRepository.findByAccount(visitingAccount));
                model.addAttribute("viewFirstAbilities", this.abilityService.viewFirstAbilities(visitingAccount));
                model.addAttribute("viewLastAbilities", this.abilityService.viewLastAbilities(visitingAccount));
                model.addAttribute("visitingaccount", visitingAccount);
                model.addAttribute("visitingProfilePicture", visitingAccount.getStockProfilePicture());
                return "homevisiting";
            }
            Ability ability = this.abilityRepository.getOne(abilityid);
            this.abilityService.newPraise(ability, useralias, praisetext, visitingAccount);
            return "redirect:/EmployeeSearch/Users/" + useralias + "/Visiting/" + visitingalias;
        }
    }

    // LOGGED IN
    // DELETE-REQUESTS
    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/Abilities/{abilityid}/DeleteAbility", method = RequestMethod.DELETE)
    public String deleteAbility(Model model, @ModelAttribute Ability ability,
            @PathVariable String useralias, @PathVariable Long abilityid) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            this.abilityService.deleteAbility(useralias, this.abilityRepository.getOne(abilityid));
            return "redirect:/EmployeeSearch/Users/" + useralias;
        }
    }
}
