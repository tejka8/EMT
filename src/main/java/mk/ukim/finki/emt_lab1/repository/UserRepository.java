
package mk.ukim.finki.emt_lab1.repository;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(
            type = EntityGraphType.FETCH,
            attributePaths = {"username", "password", "name", "surname", "role", "isAccountNonExpired", "isAccountNonLocked", "isCredentialsNonExpired", "isEnabled"}
    )
    @Query("select u from User u")
    List<User> findAll();

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
}
