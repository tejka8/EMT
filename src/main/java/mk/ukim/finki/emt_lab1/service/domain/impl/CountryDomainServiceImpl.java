
package mk.ukim.finki.emt_lab1.service.domain.impl;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.model.domain.Country;
import mk.ukim.finki.emt_lab1.repository.CountryRepository;
import mk.ukim.finki.emt_lab1.service.domain.CountryDomainService;
import org.springframework.stereotype.Service;

@Service
public class CountryDomainServiceImpl implements CountryDomainService {
    private final CountryRepository countryRepository;

    public CountryDomainServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> findAll() {
        return this.countryRepository.findAll();
    }

    public Optional<Country> findById(Long id) {
        return this.countryRepository.findById(id);
    }

    public Optional<Country> save(Country country) {
        return Optional.of((Country)this.countryRepository.save(country));
    }

    public Optional<Country> update(Long id, Country country) {
        return this.countryRepository.findById(id).map((existingCountry) -> {
            if (country.getName() != null) {
                existingCountry.setName(country.getName());
            }

            if (country.getContinent() != null) {
                existingCountry.setContinent(country.getContinent());
            }

            return (Country)this.countryRepository.save(existingCountry);
        });
    }

    public void deleteById(Long id) {
        this.countryRepository.deleteById(id);
    }
}
