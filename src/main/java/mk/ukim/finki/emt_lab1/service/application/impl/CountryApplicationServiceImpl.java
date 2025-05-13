
package mk.ukim.finki.emt_lab1.service.application.impl;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.CountryCreateDto;
import mk.ukim.finki.emt_lab1.dto.CountryUpdateDto;
import mk.ukim.finki.emt_lab1.service.application.CountryApplicationService;
import mk.ukim.finki.emt_lab1.service.domain.CountryDomainService;
import org.springframework.stereotype.Service;

@Service
public class CountryApplicationServiceImpl implements CountryApplicationService {
    public final CountryDomainService countryDomainService;

    public CountryApplicationServiceImpl(CountryDomainService countryDomainService) {
        this.countryDomainService = countryDomainService;
    }

    public List<CountryUpdateDto> findAll() {
        return this.countryDomainService.findAll().stream().map(CountryUpdateDto::from).toList();
    }

    public Optional<CountryCreateDto> save(CountryCreateDto countryCreateDto) {
        return this.countryDomainService.save(countryCreateDto.toCountry()).map(CountryCreateDto::from);
    }

    public Optional<CountryUpdateDto> findById(Long id) {
        return this.countryDomainService.findById(id).map(CountryUpdateDto::from);
    }

    public Optional<CountryUpdateDto> update(Long id, CountryUpdateDto countryUpdateDto) {
        return this.countryDomainService.update(id, countryUpdateDto.toCountry()).map(CountryUpdateDto::from);
    }

    public void deleteById(Long id) {
        this.countryDomainService.deleteById(id);
    }
}
