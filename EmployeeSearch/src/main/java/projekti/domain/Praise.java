package projekti.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class Praise extends AbstractPersistable<Long> {

    private String useralias;

    @NotEmpty
    @Size(min = 1, max = 1000)
    @Column(columnDefinition = "TEXT")
    private String praisetext;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Ability ability;
}
