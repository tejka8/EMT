
package mk.ukim.finki.emt_lab1.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import mk.ukim.finki.emt_lab1.dto.AuthorCreateDto;
import mk.ukim.finki.emt_lab1.dto.AuthorUpdateDto;
import mk.ukim.finki.emt_lab1.dto.AuthorsPerCountryDto;
import mk.ukim.finki.emt_lab1.model.domain.Author;
import mk.ukim.finki.emt_lab1.model.projections.AuthorNameProjections;
import mk.ukim.finki.emt_lab1.repository.AuthorRepository;
import mk.ukim.finki.emt_lab1.repository.views.AuthorsPerCountryViewRepository;
import mk.ukim.finki.emt_lab1.service.application.AuthorApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
        origins = {"http://localhost:3000"}
)
@RestController
@RequestMapping({"/api/authors"})
@Tag(
        name = "Author API",
        description = "Endpoints for managing authors"
)
public class AuthorController {
    private final AuthorApplicationService authorService;
    private final AuthorsPerCountryViewRepository authorsPerCountryViewRepository;
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorApplicationService authorService, AuthorsPerCountryViewRepository authorsPerCountryViewRepository, AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.authorsPerCountryViewRepository = authorsPerCountryViewRepository;
        this.authorRepository = authorRepository;
    }

    @Operation(
            summary = "Get all authors",
            description = "Retrieves a list of all available authors."
    )
    @GetMapping
    public List<Author> findAll() {
        return this.authorService.findAll();
    }

    @Operation(
            summary = "Get authors by ID",
            description = "Finds a author by its ID."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Author> findById(@PathVariable Long id) {
        return (ResponseEntity)this.authorService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Add a new author",
            description = "Creates a new author based on the given AuthorCreateDto"
    )
    @PostMapping({"/add"})
    public ResponseEntity<AuthorCreateDto> save(@RequestBody AuthorCreateDto product) {
        return (ResponseEntity)this.authorService.save(product).map(ResponseEntity::ok).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }

    @Operation(
            summary = "Update an existing author",
            description = "Updates a author by ID using AuthorUpdateDto."
    )
    @PutMapping({"/edit/{id}"})
    public ResponseEntity<AuthorUpdateDto> update(@PathVariable Long id, @RequestBody AuthorUpdateDto authorDto) {
        return (ResponseEntity)this.authorService.update(id, authorDto).map(ResponseEntity::ok).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
    }

    @Operation(
            summary = "Delete a author",
            description = "Deletes a author by its ID."
    )
    @DeleteMapping({"/delete/{id}"})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (this.authorService.findById(id).isPresent()) {
            this.authorService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get number of authors per country",
            description = "Materialized view, refreshed on author changes."
    )
    @GetMapping({"/by-country"})
    public List<AuthorsPerCountryDto> getAuthorsPerCountry() {
        return this.authorsPerCountryViewRepository.findAll().stream().map((view) -> {
            return new AuthorsPerCountryDto(view.getCountryId(), view.getNumAuthors());
        }).toList();

    }

    @Operation(
            summary = "Get author names",
            description = "Returns only name and surname using projection."
    )
    @GetMapping({"/names"})
    public List<AuthorNameProjections> getAuthorNames() {
        return this.authorRepository.findAllBy();
    }
}
