package projekti.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ability extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 1, max = 4000)
    @Column(columnDefinition = "TEXT")
    private String abilitytext;

    @ManyToOne
    private Account account;

    private int praises;

    @OneToMany(mappedBy = "ability")
    private List<Praise> praisers = new ArrayList<>();
}
