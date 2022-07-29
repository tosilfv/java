package projekti.logic.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projekti.logic.utility.CustomDate;

@Controller
public class ResourceErrorController implements ErrorController {

    @Autowired
    private CustomDate date;

    @Override
    public String getErrorPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("date", this.date.date());
        return "resource_error";
    }
}
