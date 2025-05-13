
package mk.ukim.finki.emt_lab1.repository.views;

import jakarta.transaction.Transactional;
import java.util.List;
import mk.ukim.finki.emt_lab1.model.views.BooksPerAuthorView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksPerAuthorViewRepository extends JpaRepository<BooksPerAuthorView, Long> {
    @Transactional
    @Modifying
    @Query(
            value = "REFRESH MATERIALIZED VIEW book_by_author",
            nativeQuery = true
    )
    void refreshMaterializedView();

    List<BooksPerAuthorView> findAll();
}