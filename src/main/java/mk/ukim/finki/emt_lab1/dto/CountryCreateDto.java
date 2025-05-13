package mk.ukim.finki.emt_lab1.dto;

import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.emt_lab1.model.domain.Country;

public record CountryCreateDto(String name, String continent) {
    public CountryCreateDto(String name, String continent) {
        this.name = name;
        this.continent = continent;
    }

    public static CountryCreateDto from(Country country) {
        return new CountryCreateDto(country.getName(), country.getContinent());
    }

    public static List<CountryCreateDto> from(List<Country> countries) {
        return (List)countries.stream().map(CountryCreateDto::from).collect(Collectors.toList());
    }

    public Country toCountry() {
        return new Country(this.name, this.continent);
    }

    public String name() {
        return this.name;
    }

    public String continent() {
        return this.continent;
    }
}