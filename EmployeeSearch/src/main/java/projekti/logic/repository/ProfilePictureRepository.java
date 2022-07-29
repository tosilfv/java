package projekti.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.domain.ProfilePicture;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {

    ProfilePicture findByUseralias(String useralias);
}
