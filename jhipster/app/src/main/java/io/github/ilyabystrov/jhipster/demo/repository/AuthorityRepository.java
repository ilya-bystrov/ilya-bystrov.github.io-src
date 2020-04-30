package io.github.ilyabystrov.jhipster.demo.repository;

import io.github.ilyabystrov.jhipster.demo.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
