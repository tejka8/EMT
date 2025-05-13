package mk.ukim.finki.emt_lab1.dto;

import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Country;

public record AuthorUpdateDto(Long id, String name, String surname, Long countryId) {
    public AuthorUpdateDto(Long id, String name, String surname, Long countryId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.countryId = countryId;
    }

    public static AuthorUpdateDto from(Author author) {
        return new AuthorUpdateDto(author.getId(), author.getName(), author.getSurname(), author.getCountry().getId());
    }

    public static List<AuthorUpdateDto> from(List<Author> authors) {
        return (List)authors.stream().map(AuthorUpdateDto::from).collect(Collectors.toList());
    }

    public Author toAuthor(Country country) {
        return new Author(this.name, this.surname, country);
    }

    public Long id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public String surname() {
        return this.surname;
    }

    public Long countryId() {
        return this.countryId;
    }
}
