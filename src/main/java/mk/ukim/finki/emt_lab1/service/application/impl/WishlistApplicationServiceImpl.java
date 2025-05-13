
package mk.ukim.finki.emt_lab1.service.application.impl;

import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.WishlistDto;
import mk.ukim.finki.emt_lab1.service.application.WishlistApplicationService;
import mk.ukim.finki.emt_lab1.service.domain.WishlistService;
import org.springframework.stereotype.Service;

@Service
public class WishlistApplicationServiceImpl implements WishlistApplicationService {
    private final WishlistService wishlistService;

    public WishlistApplicationServiceImpl(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    public Optional<WishlistDto> findWishListByUsername(String username) {
        return this.wishlistService.findWishListByUsername(username).map(WishlistDto::from);
    }

    public WishlistDto addBookToWishList(String username, Long bookId) {
        return WishlistDto.from(this.wishlistService.addBookToWishList(username, bookId));
    }

    public void rentAllBooksFromWishlist(String username) {
        this.wishlistService.rentAllBooksFromWishlist(username);
    }

    public void deleteByUsername(String username) {
        this.wishlistService.deleteByUsername(username);
    }
}
