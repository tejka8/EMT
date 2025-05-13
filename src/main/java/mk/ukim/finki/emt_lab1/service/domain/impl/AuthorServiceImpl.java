
package mk.ukim.finki.emt_lab1.service.domain.impl;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.events.AuthorCreatedEvent;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Country;
import mk.ukim.finki.emt_lab1.repository.AuthorRepository;
import mk.ukim.finki.emt_lab1.repository.views.AuthorsPerCountryViewRepository;
import mk.ukim.finki.emt_lab1.service.domain.AuthorDomainService;
import mk.ukim.finki.emt_lab1.service.domain.CountryDomainService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorDomainService {
    private final AuthorRepository authorRepository;
    private final CountryDomainService countryDomainService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AuthorsPerCountryViewRepository authorsPerCountryViewRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, CountryDomainService countryDomainService, ApplicationEventPublisher applicationEventPublisher, AuthorsPerCountryViewRepository authorsPerCountryViewRepository) {
        this.authorRepository = authorRepository;
        this.countryDomainService = countryDomainService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.authorsPerCountryViewRepository = authorsPerCountryViewRepository;
    }

    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }

    public Optional<Author> save(Author author) {
        return author.getCountry() != null && this.countryDomainService.findById(author.getCountry().getId()).isPresent() ? Optional.of((Author)this.authorRepository.save(new Author(author.getName(), author.getSurname(), (Country)this.countryDomainService.findById(author.getCountry().getId()).get()))) : Optional.empty();
    }

    public Optional<Author> update(Long id, Author author) {
        return this.authorRepository.findById(id).map((existingAuthor) -> {
            if (author.getName() != null) {
                existingAuthor.setName(author.getName());
            }

            if (author.getSurname() != null) {
                existingAuthor.setSurname(author.getSurname());
            }

            if (author.getCountry().getId() != null) {
                Optional<Country> country = this.countryDomainService.findById(author.getCountry().getId());
                if (country.isPresent()) {
                    existingAuthor.setCountry((Country)country.get());
                }
            }

            return (Author)this.authorRepository.save(existingAuthor);
        });
    }

    public void deleteById(Long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        author.ifPresent((a) -> {
            this.authorRepository.deleteById(id);
            this.applicationEventPublisher.publishEvent(new AuthorCreatedEvent(a));
        });
    }

    public void refreshMaterializedView() {
        this.authorsPerCountryViewRepository.refreshMaterializedView();
    }
}
