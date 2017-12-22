package io.github.ilyabystrov.urlshortener.urlshortener.domain;

import io.github.ilyabystrov.urlshortener.urlshortener.domain.entity.Reference;
import org.springframework.data.repository.Repository;

public interface LinkRepository extends Repository<Reference, Long> {
}
