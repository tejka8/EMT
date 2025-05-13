package mk.ukim.finki.emt_lab1.service.application;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.CountryCreateDto;
import mk.ukim.finki.emt_lab1.dto.CountryUpdateDto;

public interface CountryApplicationService {
    List<CountryUpdateDto> findAll();

    Optional<CountryCreateDto> save(CountryCreateDto countryCreateDto);

    Optional<CountryUpdateDto> findById(Long id);

    Optional<CountryUpdateDto> update(Long id, CountryUpdateDto countryUpdateDto);

    void deleteById(Long id);
}
