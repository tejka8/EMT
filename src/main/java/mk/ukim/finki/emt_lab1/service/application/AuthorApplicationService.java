package mk.ukim.finki.emt_lab1.service.application;

import java.util.List;
import java.util.Optional;
import mk.ukim.finki.emt_lab1.dto.AuthorCreateDto;
import mk.ukim.finki.emt_lab1.dto.AuthorUpdateDto;
import mk.ukim.finki.emt_lab1.model.domain.Author;

public interface AuthorApplicationService {
    List<Author> findAll();

    Optional<Author> findById(Long id);

    Optional<AuthorCreateDto> save(AuthorCreateDto authorCreateDto);

    Optional<AuthorUpdateDto> update(Long id, AuthorUpdateDto authorUpdateDto);

    void deleteById(Long id);
}
