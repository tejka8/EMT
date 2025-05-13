
package mk.ukim.finki.emt_lab1.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Country;
import mk.ukim.finki.emt_lab1.service.domain.CountryDomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
        origins = {"http://localhost:3000"}
)
@RestController
@RequestMapping({"/api/countries"})
@Tag(
        name = "Country API",
        description = "Endpoints for managing countries"
)
public class CountryController {
    private final CountryDomainService countryDomainService;

    public CountryController(CountryDomainService countryDomainService) {
        this.countryDomainService = countryDomainService;
    }

    @Operation(
            summary = "Get all countries",
            description = "Retrieves a list of all available counties."
    )
    @GetMapping
    public List<Country> findAll() {
        return this.countryDomainService.findAll();
    }

    @Operation(
            summary = "Get country by ID",
            description = "Finds a country by its ID."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Country> findById(@PathVariable Long id) {
        return (ResponseEntity)this.countryDomainService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Add a new country",
            description = "Creates a new country based on the given CreateCountryDto"
    )
    @PostMapping({"/add"})
    public Optional<Country> save(@RequestBody Country product) {
        return this.countryDomainService.save(product);
    }
}
