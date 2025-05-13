
package mk.ukim.finki.emt_lab1.service.application.impl;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.AuthorCreateDto;
import mk.ukim.finki.emt_lab1.dto.AuthorUpdateDto;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Country;
import mk.ukim.finki.emt_lab1.service.application.AuthorApplicationService;
import mk.ukim.finki.emt_lab1.service.domain.AuthorDomainService;
import mk.ukim.finki.emt_lab1.service.domain.CountryDomainService;
import org.springframework.stereotype.Service;

@Service
public class AuthorApplicationServiceImpl implements AuthorApplicationService {
    private final AuthorDomainService authorDomainService;
    private final CountryDomainService countryDomainService;

    public AuthorApplicationServiceImpl(AuthorDomainService authorDomainService, CountryDomainService countryDomainService) {
        this.authorDomainService = authorDomainService;
        this.countryDomainService = countryDomainService;
    }

    public List<Author> findAll() {
        return this.authorDomainService.findAll();
    }

    public Optional<Author> findById(Long id) {
        return this.authorDomainService.findById(id);
    }

    public Optional<AuthorCreateDto> save(AuthorCreateDto authorCreateDto) {
        Optional<Country> country = this.countryDomainService.findById(authorCreateDto.countryId());
        return country.isPresent() ? this.authorDomainService.save(authorCreateDto.toAuthor((Country)country.get())).map(AuthorCreateDto::from) : Optional.empty();
    }

    public Optional<AuthorUpdateDto> update(Long id, AuthorUpdateDto authorUpdateDto) {
        Optional<Country> country = this.countryDomainService.findById(authorUpdateDto.countryId());
        Country countryEntity = country.orElse(null); // дозволува null
        return this.authorDomainService.update(id, authorUpdateDto.toAuthor(countryEntity))
                .map(AuthorUpdateDto::from);
    }

    public void deleteById(Long id) {
        this.authorDomainService.deleteById(id);
    }
}
