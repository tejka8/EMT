package mk.ukim.finki.emt_lab1.dto;



import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.emt_lab1.model.domain.Country;

public record CountryUpdateDto(Long id, String name, String continent) {
    public CountryUpdateDto(Long id, String name, String continent) {
        this.id = id;
        this.name = name;
        this.continent = continent;
    }

    public static CountryUpdateDto from(Country country) {
        return new CountryUpdateDto(country.getId(), country.getName(), country.getContinent());
    }

    public static List<CountryUpdateDto> from(List<Country> countries) {
        return (List)countries.stream().map(CountryUpdateDto::from).collect(Collectors.toList());
    }

    public Country toCountry() {
        return new Country(this.name, this.continent);
    }

    public Long id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public String continent() {
        return this.continent;
    }
}
