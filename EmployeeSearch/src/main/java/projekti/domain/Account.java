package projekti.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.logic.service.RegisterService.ValidPassword;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 60)
    private String username;

    @ValidPassword
    @NotEmpty
    @NotNull
    @Size(min = 8, max = 60)
    private String password;

    @NotEmpty
    @NotNull
    @Size(min = 8, max = 60)
    private String confirm;

    @NotEmpty
    @NotNull
    @Size(min = 6, max = 30)
    private String realname;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 30)
    private String useralias;

    private boolean submittedProfilePicture = false;

    private String stockProfilePicture = "neutral";

    @ElementCollection
    private List<String[]> connectionRequestsReceived = new ArrayList<>();

    @ElementCollection
    private List<String[]> connectionRequestsSent = new ArrayList<>();

    @ElementCollection
    private List<String> establishedUseraliases = new ArrayList<>();

    @ElementCollection
    private List<String[]> connectionsEstablished = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private List<Ability> abilities = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Praise> praisers = new ArrayList<>();
}
