package mk.ukim.finki.emt_lab1.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Subselect("SELECT * FROM book_by_author")
@Immutable
public class BooksPerAuthorView {
    @Id
    @Column(
            name = "author_id"
    )
    private Long authorId;
    @Column(
            name = "number_of_books"
    )
    private Integer numBooks;

    public BooksPerAuthorView() {
    }

    public Long getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Integer getNumBooks() {
        return this.numBooks;
    }

    public void setNumBooks(Integer numBooks) {
        this.numBooks = numBooks;
    }
}
