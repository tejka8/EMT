package mk.ukim.finki.emt_lab1.service.application;

import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.WishlistDto;

public interface WishlistApplicationService {
    Optional<WishlistDto> findWishListByUsername(String username);

    WishlistDto addBookToWishList(String username, Long bookId);

    void rentAllBooksFromWishlist(String username);

    void deleteByUsername(String username);
}
