
package mk.ukim.finki.emt_lab1.dto;

import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.domain.Book;
import mk.ukim.finki.emt_lab1.model.enumerations.Category;

public record UpdateBookDto(Long id, String name, Integer avaliableCopies, Long author, String category) {
    public UpdateBookDto(Long id, String name, Integer avaliableCopies, Long author, String category) {
        this.id = id;
        this.name = name;
        this.avaliableCopies = avaliableCopies;
        this.author = author;
        this.category = category;
    }

    public static UpdateBookDto from(Book book) {
        return new UpdateBookDto(book.getId(), book.getName(), book.getAvaliableCopies(), book.getAuthor().getId(), book.getCategory().toString());
    }

    public static List<UpdateBookDto> from(List<Book> books) {
        return (List)books.stream().map(UpdateBookDto::from).collect(Collectors.toList());
    }

    public Book toBook(Book existingBook, Author newAuthor, Category newCategory) {
        existingBook.setName(this.name);
        existingBook.setAvaliableCopies(this.avaliableCopies);
        existingBook.setAuthor(newAuthor);
        existingBook.setCategory(newCategory);
        return existingBook;
    }

    public Long id() {
        return this.id;
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
