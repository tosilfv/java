package projekti.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Comment extends AbstractPersistable<Long> {

    private Long postid;

    private String useralias;

    private String postingtime;

    @NotEmpty
    @Size(min = 1, max = 4000)
    @Column(columnDefinition = "TEXT")
    private String response;
}
