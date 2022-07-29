package projekti.logic.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.domain.Account;
import projekti.domain.Post;
import projekti.logic.repository.AccountRepository;
import projekti.logic.repository.PostsRepository;
import projekti.logic.utility.CustomDate;

@Service
public class PostsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomDate date;

    @Autowired
    private PostsRepository postsRepository;

    // Returns the establishedUseraliases list of parameter useralias account
    public List<String> establishedUseraliases(String useralias) {
        Account account = this.accountRepository.findByUseralias(useralias);
        List<String> connections = account.getEstablishedUseraliases();
        return connections;
    }

    // Returns the value (page number) of the last index in parameter list
    public Integer lastPage(List<Integer> totalPages) {
        int size = totalPages.size();
        Integer lastPage = totalPages.get(size - 1);
        switch (size) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return lastPage;
        }
    }

    public Long newPost(Post post, String useralias, String title, String message) {
        post = new Post();
        Account account = this.accountRepository.findByUseralias(useralias);
        post.setUseralias(account.getUseralias());
        post.setPostingtime(this.date.dateTime());
        post.setTitle(title);
        post.setMessage(message);
        post.setLikes(0);
        this.postsRepository.save(post);
        return post.getId();
    }

    // Returns a list of five page numbers for pagination
    public List<Integer> totalPages(int postsPerPage, int showpagerecent, String useralias) {
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPosts = totalPosts(useralias);
        if (totalPosts > postsPerPage) {
            int startPage = showpagerecent;
            int endPage = 0, additionalPages = 0, zeroPages = 0;
            int onePage = 1, twoPages = 2, threePages = 3, fourPages = 4;
            if (totalPosts % postsPerPage > zeroPages) {
                additionalPages = onePage;
            }
            int totalPages = totalPosts / postsPerPage + additionalPages;
            if (totalPages > startPage) {
                if (startPage + fourPages < totalPages) {
                    endPage = startPage + fourPages;
                } else if (startPage + fourPages < totalPages + onePage) {
                    endPage = startPage + threePages;
                    startPage = startPage - onePage;
                } else if (startPage + fourPages < totalPages + twoPages) {
                    endPage = startPage + twoPages;
                    startPage = startPage - twoPages;
                } else if (startPage + fourPages < totalPages + threePages) {
                    endPage = startPage + onePage;
                    startPage = startPage - threePages;
                } else if (startPage + fourPages < totalPages + fourPages) {
                    endPage = startPage;
                    startPage = startPage - fourPages;
                }
            }
            for (int i = startPage; i <= endPage; i++) {
                if (i < 0 || i == totalPages) {
                    continue;
                }
                pageNumbers.add(i);
            }
        } else {
            pageNumbers.add(0);
        }
        return pageNumbers;
    }

    // Returns the integer amount of posts that user or user's connections have created
    public int totalPosts(String useralias) {
        int totalposts = 0;
        Account account = this.accountRepository.findByUseralias(useralias);
        List<String> establishedUseraliases = account.getEstablishedUseraliases();
        List<Post> allPosts = this.postsRepository.findAll();
        for (Post p : allPosts) {
            if (p.getUseralias().equals(useralias)
                    || establishedUseraliases.contains(p.getUseralias())) {
                totalposts++;
            }
        }
        return totalposts;
    }
}
