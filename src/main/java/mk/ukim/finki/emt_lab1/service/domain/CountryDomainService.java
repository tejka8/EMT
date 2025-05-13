package mk.ukim.finki.emt_lab1.service.domain;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Country;

public interface CountryDomainService {
    List<Country> findAll();

    Optional<Country> findById(Long id);

    Optional<Country> save(Country country);

    Optional<Country> update(Long id, Country country);

    void deleteById(Long id);
}
