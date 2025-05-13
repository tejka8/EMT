
package mk.ukim.finki.emt_lab1.repository;

import java.util.List;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.User;
import mk.ukim.finki.emt_lab1.model.domain.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    List<UserBook> findUserBooksByBookId(Long bookId);

    @Query("SELECT ub.bookId FROM UserBook ub GROUP BY ub.bookId ORDER BY COUNT(ub.bookId) DESC LIMIT 1")
    Long findMostRentedBook();

    @Query("SELECT r.username FROM UserBook r GROUP BY r.username ORDER BY COUNT(r.username) DESC LIMIT 1")
    User findMostActiveUser();

    @Query("SELECT r.username FROM UserBook r JOIN Author a ORDER BY COUNT(a.name)")
    Author findMostRentedAuthor();
}
