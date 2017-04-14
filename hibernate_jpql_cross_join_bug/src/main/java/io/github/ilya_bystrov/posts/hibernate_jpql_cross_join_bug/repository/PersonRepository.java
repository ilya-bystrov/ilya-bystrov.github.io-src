package io.github.ilya_bystrov.posts.hibernate_jpql_cross_join_bug.repository;

import io.github.ilya_bystrov.posts.hibernate_jpql_cross_join_bug.domain.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PersonRepository extends Repository<Person, Long> {

    @Query(value = "select p from Person p join p.streetAddress a where a.address = ?1")
    List<Person> findAllByStreetAddressLikeExplicit(String streetAdress);

    @Query(value = "select p from Person p where p.streetAddress.address = ?1")
    List<Person> findAllByStreetAddressLikeImplicit(String streetAdress);
}
