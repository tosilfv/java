package projekti.logic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostid(Long postid);
}
