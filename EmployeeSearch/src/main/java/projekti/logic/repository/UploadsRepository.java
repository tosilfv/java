package projekti.logic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.Upload;

public interface UploadsRepository extends JpaRepository<Upload, Long> {

    List<Upload> findByUseralias(String useralias);
}
