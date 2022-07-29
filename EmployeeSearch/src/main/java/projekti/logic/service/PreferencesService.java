package projekti.logic.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projekti.domain.Account;
import projekti.domain.ProfilePicture;
import projekti.logic.repository.AccountRepository;
import projekti.logic.repository.ProfilePictureRepository;

@Service
public class PreferencesService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    // Deletes current profilepicture from repository of parameter useralias if it's there
    public void deleteLoadedProfilePicture(String useralias) {
        ProfilePicture deletePreviousProfilePicture = this.profilePictureRepository.findByUseralias(useralias);
        if (deletePreviousProfilePicture != null) {
            this.profilePictureRepository.delete(deletePreviousProfilePicture);
        }
    }

    // Saves parameter loadedProfilePicture to repository and sets parameter useralias account submittedProfilePicture to true
    public void newProfilePictureLoad(String useralias, MultipartFile loadedProfilePicture) throws IOException {
        if (loadedProfilePicture.isEmpty()) {
            return;
        }
        if (!loadedProfilePicture.getContentType().contains("image/")) {
            return;
        }
        Account account = this.accountRepository.findByUseralias(useralias);
        deleteLoadedProfilePicture(useralias);
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setUseralias(useralias);
        profilePicture.setName(loadedProfilePicture.getOriginalFilename());
        profilePicture.setMediaType(loadedProfilePicture.getContentType());
        profilePicture.setProfilepictureSize(loadedProfilePicture.getSize());
        profilePicture.setProfilepicture(loadedProfilePicture.getBytes());
        this.profilePictureRepository.save(profilePicture);
        account.setSubmittedProfilePicture(true);
        this.accountRepository.save(account);
    }

    // Sets the parameter useraliast account stockProfilePicture to parameter profilePictureStock and submittedProfilePicture to false
    public void newProfilePictureStock(String useralias, String profilePictureStock) {
        Account account = this.accountRepository.findByUseralias(useralias);
        deleteLoadedProfilePicture(useralias);
        account.setStockProfilePicture(profilePictureStock);
        account.setSubmittedProfilePicture(false);
        this.accountRepository.save(account);
    }
}
