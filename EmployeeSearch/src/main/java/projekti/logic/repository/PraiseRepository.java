package projekti.logic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Account;
import projekti.domain.Praise;

public interface PraiseRepository extends JpaRepository<Praise, Long> {

    List<Praise> findByAccount(Account account);
}
