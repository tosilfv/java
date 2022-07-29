package projekti.logic.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.logic.service.HomeService;
import projekti.logic.service.SearchService;

@Controller
public class SearchController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private SearchService searchService;

    // LOGGED IN
    // GET-REQUESTS
    @Secured("USER")
    @GetMapping("/EmployeeSearch/Users/{useralias}/Search")
    public String userSearch(Model model, @PathVariable String useralias,
            @RequestParam String keyword) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            if (keyword.length() == 0) {
                return "search";
            }
            model.addAttribute("searchResultsRealname", this.searchService.searchRealnames(useralias, keyword));
            model.addAttribute("searchResultsUseralias", this.searchService.searchUseraliases(useralias, keyword));
            return "search";
        }
    }

    @Secured("USER")
    @GetMapping("/EmployeeSearch/Users/{useralias}/SearchDropdown")
    public String userSearchDropdown(Model model, @PathVariable String useralias) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            return "search";
        }
    }
}
