package projekti.logic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Ability;
import projekti.domain.Account;

public interface AbilityRepository extends JpaRepository<Ability, Long> {

    List<Ability> findByAccount(Account account);
}
