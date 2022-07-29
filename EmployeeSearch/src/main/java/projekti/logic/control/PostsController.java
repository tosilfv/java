package projekti.logic.control;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.domain.Post;
import projekti.logic.repository.PostsRepository;
import projekti.logic.service.HomeService;
import projekti.logic.service.PostsService;

@Controller
public class PostsController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostsService postsService;

    // LOGGED IN
    // GET-REQUESTS
    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/Posts", method = RequestMethod.GET)
    public String postsWall(Model model, @ModelAttribute Post post,
            @PathVariable String useralias, @RequestParam(defaultValue = "0") String showpagerecent,
            @RequestParam(defaultValue = "0") String showpagefollowed) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            int postsPerPage = 25;
            Pageable pageable = PageRequest.of(Integer.parseInt(showpagerecent), postsPerPage, Sort.by("postingtime").descending());
            List<Integer> pages = this.postsService.totalPages(postsPerPage, Integer.parseInt(showpagerecent), useralias);
            model.addAttribute("currentPageRecent", Integer.parseInt(showpagerecent));
            model.addAttribute("establishedUseraliases", this.postsService.establishedUseraliases(useralias));
            model.addAttribute("lastPage", this.postsService.lastPage(pages));
            model.addAttribute("totalPagesRecent", pages);
            model.addAttribute("totalPagesRecentSize", this.postsService.totalPosts(useralias));
            model.addAttribute("viewAllPosts", this.postsRepository.findAll(pageable));
            return "posts";
        }
    }

    // LOGGED IN
    // POST-REQUESTS
    @Secured("USER")
    @RequestMapping(value = "/EmployeeSearch/Users/{useralias}/Posts", method = RequestMethod.POST)
    public String postNew(Model model, @Valid @ModelAttribute Post post,
            BindingResult bindingResult, @PathVariable String useralias,
            @RequestParam(defaultValue = "0") String showpagerecent,
            @RequestParam String title, @RequestParam String message) {
        if (this.homeService.helloUser(model, useralias) == false) {
            return "fragments/layout_address_error";
        } else {
            if (bindingResult.hasErrors()) {
                int postsPerPage = 25;
                Pageable pageable = PageRequest.of(0, postsPerPage, Sort.by("postingtime").descending());
                List<Integer> pages = this.postsService.totalPages(postsPerPage, Integer.parseInt(showpagerecent), useralias);
                model.addAttribute("currentPageRecent", Integer.parseInt(showpagerecent));
                model.addAttribute("establishedUseraliases", this.postsService.establishedUseraliases(useralias));
                model.addAttribute("lastPage", this.postsService.lastPage(pages));
                model.addAttribute("totalPagesRecent", pages);
                model.addAttribute("totalPagesRecentSize", this.postsService.totalPosts(useralias));
                model.addAttribute("viewAllPosts", this.postsRepository.findAll(pageable));
                return "posts";
            }
            Long postid = this.postsService.newPost(post, useralias, title, message);
            return "redirect:/EmployeeSearch/Users/" + useralias + "/Comments/" + postid;
        }
    }
}
