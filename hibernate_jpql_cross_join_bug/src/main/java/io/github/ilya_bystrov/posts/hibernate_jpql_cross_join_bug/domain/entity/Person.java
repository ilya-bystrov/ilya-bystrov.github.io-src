package io.github.ilya_bystrov.posts.hibernate_jpql_cross_join_bug.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
public class Person {

    @Id
    private Long id;

    private String firstname;

    private String lastname;

    @OneToOne
    @JoinColumn(name = "id")
    private StreetAddress streetAddress;
}
