package mk.ukim.finki.emt_lab1.repository.views;

import mk.ukim.finki.emt_lab1.model.views.AuthorsPerCountryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuthorsPerCountryViewRepository extends JpaRepository<AuthorsPerCountryView, Long> {
    @Transactional
    @Modifying
    @Query(
            value = "REFRESH MATERIALIZED VIEW authors_per_country",
            nativeQuery = true
    )
    void refreshMaterializedView();
}
