package mk.ukim.finki.emt_lab1.repository;

import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findWishlistByUserUsername(String username);

    void deleteByUserUsername(String username);
}
