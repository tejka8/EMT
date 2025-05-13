
package mk.ukim.finki.emt_lab1.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.WishlistDto;
import mk.ukim.finki.emt_lab1.service.application.WishlistApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"api/wishlist"})
@Tag(
        name = "Wishlist API",
        description = "Endpoints for managing user wishlists"
)
public class WishlistController {
    private final WishlistApplicationService wishlistApplicationService;

    public WishlistController(WishlistApplicationService wishlistApplicationService) {
        this.wishlistApplicationService = wishlistApplicationService;
    }

    @Operation(
            summary = "Get wishlist by username",
            description = "Retrieves the wishlist for a given username."
    )
    @GetMapping
    public Optional<WishlistDto> findWishlistByUsername(@RequestParam String username) {
        return this.wishlistApplicationService.findWishListByUsername(username);
    }

    @Operation(
            summary = "Add book to wishlist",
            description = "Add a book to the user's wishlist."
    )
    @PostMapping({"/add/{bookId}"})
    public ResponseEntity<WishlistDto> addBookToWishlist(@PathVariable Long bookId, @RequestParam String username) {
        try {
            return ResponseEntity.ok(this.wishlistApplicationService.addBookToWishList(username, bookId));
        } catch (Exception var4) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(
            summary = "Rent a book",
            description = "Decreases the number of available copies if possible."
    )
    @PostMapping({"/rent"})
    public ResponseEntity<String> rentWishListBooks(@RequestParam String username) {
        try {
            this.wishlistApplicationService.rentAllBooksFromWishlist(username);
            return ResponseEntity.ok("All books from the wishlist have been rented successfully.");
        } catch (Exception var3) {
            return ResponseEntity.badRequest().body(var3.getMessage());
        }
    }

    @Operation(
            summary = "Delete wishlist",
            description = "Delete all books from the user's wishlist."
    )
    @DeleteMapping({"/delete"})
    public void deleteWishlistBooks(@RequestParam String username) {
        this.wishlistApplicationService.deleteByUsername(username);
    }
}
