
package mk.ukim.finki.emt_lab1.config;

import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Book;
import mk.ukim.finki.emt_lab1.model.domain.Country;
import mk.ukim.finki.emt_lab1.model.domain.User;
import mk.ukim.finki.emt_lab1.model.enumerations.Category;
import mk.ukim.finki.emt_lab1.model.enumerations.Role;
import mk.ukim.finki.emt_lab1.repository.AuthorRepository;
import mk.ukim.finki.emt_lab1.repository.BookRepository;
import mk.ukim.finki.emt_lab1.repository.CountryRepository;
import mk.ukim.finki.emt_lab1.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Data {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Data(BookRepository bookRepository, AuthorRepository authorRepository, CountryRepository countryRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void init() {
        Country usa = (Country)this.countryRepository.save(new Country("USA", "North America"));
        Country uk = (Country)this.countryRepository.save(new Country("UK", "Europe"));
        Country germany = (Country)this.countryRepository.save(new Country("Germany", "Europe"));
        Country france = (Country)this.countryRepository.save(new Country("France", "Europe"));
        Country japan = (Country)this.countryRepository.save(new Country("Japan", "Asia"));
        Author author1 = (Author)this.authorRepository.save(new Author("Stephen", "King", usa));
        Author author2 = (Author)this.authorRepository.save(new Author("J.K.", "Rowling", uk));
        Author author3 = (Author)this.authorRepository.save(new Author("Goethe", "Johann", germany));
        Author author4 = (Author)this.authorRepository.save(new Author("Victor", "Hugo", france));
        Author author5 = (Author)this.authorRepository.save(new Author("Haruki", "Murakami", japan));
        this.bookRepository.save(new Book("The Shining", Category.THRILLER, author1, 10));
        this.bookRepository.save(new Book("Harry Potter", Category.FANTASY, author2, 15));
        this.bookRepository.save(new Book("Faust", Category.CLASSICS, author3, 8));
        this.bookRepository.save(new Book("Les Misérables", Category.DRAMA, author4, 12));
        this.bookRepository.save(new Book("Norwegian Wood", Category.NOVEL, author5, 20));
        this.userRepository.save(new User("ts", this.passwordEncoder.encode("ts"), "Teodora", "Stojanovska", Role.LIBRARIAN));
        this.userRepository.save(new User("user", this.passwordEncoder.encode("user"), "user", "user", Role.USER));
    }
}
