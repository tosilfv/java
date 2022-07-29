package projekti.logic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Post;

public interface PostsRepository extends JpaRepository<Post, Long> {

    List<Post> findByUseralias(String useralias);
}
