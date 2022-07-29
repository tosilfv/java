package projekti.logic.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String welcomeRoot() {
        return "redirect:/EmployeeSearch/Welcome";
    }

    @GetMapping("/EmployeeSearch")
    public String welcomeEmployeeSearch() {
        return "redirect:/EmployeeSearch/Welcome";
    }

    @GetMapping("/EmployeeSearch/")
    public String welcomeEmployeeSearchSlash() {
        return "redirect:/EmployeeSearch/Welcome";
    }
}
