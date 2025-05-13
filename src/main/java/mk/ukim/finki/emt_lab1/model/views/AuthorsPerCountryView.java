
package mk.ukim.finki.emt_lab1.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Subselect("SELECT * FROM authors_per_country")
@Immutable
public class AuthorsPerCountryView {
    @Id
    @Column(
            name = "country_id"
    )
    private Long countryId;
    @Column(
            name = "num_authors"
    )
    private Integer numAuthors;

    public AuthorsPerCountryView() {
    }

    public Long getCountryId() {
        return this.countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Integer getNumAuthors() {
        return this.numAuthors;
    }

    public void setNumAuthors(Integer numAuthors) {
        this.numAuthors = numAuthors;
    }
}
