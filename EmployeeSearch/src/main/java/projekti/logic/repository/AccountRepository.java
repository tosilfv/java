package projekti.logic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByRealnameContainingIgnoreCase(String keyword);

    Account findByUseralias(String useralias);

    List<Account> findByUseraliasContainingIgnoreCase(String keyword);

    Account findByUsername(String username);
}
