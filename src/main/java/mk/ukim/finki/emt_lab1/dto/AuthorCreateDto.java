package mk.ukim.finki.emt_lab1.dto;

import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Country;

public record AuthorCreateDto(String name, String surname, Long countryId) {
    public AuthorCreateDto(String name, String surname, Long countryId) {
        this.name = name;
        this.surname = surname;
        this.countryId = countryId;
    }

    public static AuthorCreateDto from(Author author) {
        return new AuthorCreateDto(author.getName(), author.getSurname(), author.getCountry().getId());
    }

    public static List<AuthorCreateDto> from(List<Author> authors) {
        return (List)authors.stream().map(AuthorCreateDto::from).collect(Collectors.toList());
    }

    public Author toAuthor(Country country) {
        return new Author(this.name, this.surname, country);
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
