package projekti.logic.control;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import projekti.domain.Ability;
import projekti.domain.Account;
import projekti.logic.repository.AccountRepository;
import projekti.logic.repository.ProfilePictureRepository;
import projekti.logic.service.HomeService;
import projekti.logic.service.PreferencesService;

@Controller
public class PreferencesController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HomeService homeService;

    @Autowired
    private PreferencesService preferencesService;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    // LOGGED IN
    // GET-REQUESTS
    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/LoadedProfilePicture/{loadedProfilePictureId}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] retrieveLoadedProfilePicture(Model model, @PathVariable String useralias,
            @PathVariable Long loadedProfilePictureId) {
        return this.profilePictureRepository.getOne(loadedProfilePictureId).getProfilepicture();
    }

    @Secured("USER")
    @GetMapping("/EmployeeSearch/Users/{useralias}/Preferences")
    public String userPreferences(Model model, @ModelAttribute Ability ability,
            @PathVariable String useralias) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            Account account = this.accountRepository.findByUseralias(useralias);
            model.addAttribute("loadedProfilePicture", this.profilePictureRepository.findByUseralias(useralias));
            model.addAttribute("submittedProfilePicture", account.isSubmittedProfilePicture());
            return "preferences";
        }
    }

    // LOGGED IN
    // POST-REQUESTS
    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/ProfilePictureLoad", method = RequestMethod.POST)
    public String profilePictureLoadNew(Model model, @RequestParam("profilePictureLoaded") MultipartFile loadedProfilePicture,
            @PathVariable String useralias) throws IOException {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            this.preferencesService.newProfilePictureLoad(useralias, loadedProfilePicture);
            return "redirect:/EmployeeSearch/Users/" + useralias;
        }
    }

    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/ProfilePictureStock", method = RequestMethod.POST)
    public String profilePictureStockNew(Model model, @PathVariable String useralias,
            @RequestParam(required = false) String profilePictureStock) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            this.preferencesService.newProfilePictureStock(useralias, profilePictureStock);
            return "redirect:/EmployeeSearch/Users/" + useralias;
        }
    }
}
