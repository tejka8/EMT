
package mk.ukim.finki.emt_lab1.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import mk.ukim.finki.emt_lab1.model.enumerations.Category;

@Entity
public class Book {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne
    private Author author;
    private Integer availableCopies;

    public Book() {
    }

    public Book(String name, Category category, Author author, Integer avaliableCopies) {
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = avaliableCopies;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Integer getAvaliableCopies() {
        return this.availableCopies;
    }

    public void setAvaliableCopies(Integer avaliableCopies) {
        this.availableCopies = avaliableCopies;
    }

    public boolean isAvailable() {
        return this.availableCopies > 0;
    }

    public void rentBook() {
        if (this.isAvailable()) {
            Integer var1 = this.availableCopies;
            this.availableCopies = this.availableCopies - 1;
        } else {
            throw new RuntimeException("Нема достапни копии за изнајмување!");
        }
    }

    public void returnBook() {
        Integer var1 = this.availableCopies;
        this.availableCopies = this.availableCopies + 1;
    }
}
