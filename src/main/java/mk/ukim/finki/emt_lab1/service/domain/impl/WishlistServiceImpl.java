
package mk.ukim.finki.emt_lab1.service.domain.impl;

import java.util.Iterator;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Book;
import mk.ukim.finki.emt_lab1.model.domain.Wishlist;
import mk.ukim.finki.emt_lab1.repository.WishlistRepository;
import mk.ukim.finki.emt_lab1.service.domain.BookDomainService;
import mk.ukim.finki.emt_lab1.service.domain.UserService;
import mk.ukim.finki.emt_lab1.service.domain.WishlistService;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final BookDomainService bookDomainService;
    private final UserService userService;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, BookDomainService bookDomainService, UserService userService) {
        this.wishlistRepository = wishlistRepository;
        this.bookDomainService = bookDomainService;
        this.userService = userService;
    }

    public Optional<Wishlist> findWishListByUsername(String username) {
        return this.wishlistRepository.findWishlistByUserUsername(username);
    }

    public Wishlist addBookToWishList(String username, Long bookId) {
        Wishlist wishlist = (Wishlist)this.wishlistRepository.findWishlistByUserUsername(username).orElseGet(() -> {
            return (Wishlist)this.wishlistRepository.save(new Wishlist(this.userService.findByUsername(username)));
        });
        Book book = (Book)this.bookDomainService.findById(bookId).orElseThrow(() -> {
            return new RuntimeException("Book not found");
        });
        if (book.getAvaliableCopies() > 0 && !wishlist.getBooks().contains(book)) {
            wishlist.getBooks().add(book);
            return (Wishlist)this.wishlistRepository.save(wishlist);
        } else {
            throw new RuntimeException("Book is not available or already in wishlist");
        }
    }

    public void rentAllBooksFromWishlist(String username) {
        Wishlist wishlist = (Wishlist)this.wishlistRepository.findWishlistByUserUsername(username).orElseThrow(() -> {
            return new RuntimeException("Wishlist not found");
        });
        Iterator var3 = wishlist.getBooks().iterator();

        while(var3.hasNext()) {
            Book book = (Book)var3.next();
            if (book.getAvaliableCopies() <= 0) {
                throw new RuntimeException("Book " + book.getName() + " is not available for rent");
            }

            book.setAvaliableCopies(book.getAvaliableCopies() - 1);
        }

        wishlist.getBooks().clear();
        this.wishlistRepository.save(wishlist);
    }

    public void deleteByUsername(String username) {
        this.wishlistRepository.deleteByUserUsername(username);
    }
}
