package mk.ukim.finki.emt_lab1.dto;

import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Book;
import mk.ukim.finki.emt_lab1.model.enumerations.Category;

public record CreateBookDto(String name, Integer avaliableCopies, Long author, String category) {
    public CreateBookDto(String name, Integer avaliableCopies, Long author, String category) {
        this.name = name;
        this.avaliableCopies = avaliableCopies;
        this.author = author;
        this.category = category;
    }

    public static CreateBookDto from(Book book) {
        return new CreateBookDto(book.getName(), book.getAvaliableCopies(), book.getAuthor().getId(), book.getCategory().toString());
    }

    public static List<CreateBookDto> from(List<Book> books) {
        return (List)books.stream().map(CreateBookDto::from).collect(Collectors.toList());
    }

    public Book toBook(Author author, Category category) {
        return new Book(this.name, category, author, this.avaliableCopies);
    }

    public String name() {
        return this.name;
    }

    public Integer avaliableCopies() {
        return this.avaliableCopies;
    }

    public Long author() {
        return this.author;
    }

    public String category() {
        return this.category;
    }
}
