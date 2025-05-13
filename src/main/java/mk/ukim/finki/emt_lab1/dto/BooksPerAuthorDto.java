package mk.ukim.finki.emt_lab1.dto;

import lombok.Data;

@Data
public class BooksPerAuthorDto{
    private Long authorId;
    private Integer numBooks;

    public BooksPerAuthorDto() {
    }

    public BooksPerAuthorDto(Long authorId, Integer numBooks) {
        this.authorId = authorId;
        this.numBooks = numBooks;
    }

    public Long getAuthorId() {
        return this.authorId;
    }

    public Integer getNumBooks() {
        return this.numBooks;
    }
}