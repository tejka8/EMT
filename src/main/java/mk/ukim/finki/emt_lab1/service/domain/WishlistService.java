package mk.ukim.finki.emt_lab1.service.domain;

import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Wishlist;

public interface WishlistService {
    Optional<Wishlist> findWishListByUsername(String username);

    Wishlist addBookToWishList(String username, Long bookId);

    void rentAllBooksFromWishlist(String username);

    void deleteByUsername(String username);
}
