package mk.ukim.finki.emt_lab1.dto;

import lombok.Data;

@Data
public class AuthorsPerCountryDto {
    private Long countryId;
    private Integer numAuthors;

    public AuthorsPerCountryDto() {
    }

    public AuthorsPerCountryDto(Long countryId, Integer numAuthors) {
        this.countryId = countryId;
        this.numAuthors = numAuthors;
    }

    public Long getCountryId() {
        return countryId;
    }

    public Integer getNumAuthors() {
        return numAuthors;
    }
}

