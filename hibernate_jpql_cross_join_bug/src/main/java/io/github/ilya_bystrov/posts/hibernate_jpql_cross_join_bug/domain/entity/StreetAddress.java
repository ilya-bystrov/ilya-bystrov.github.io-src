package io.github.ilya_bystrov.posts.hibernate_jpql_cross_join_bug.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class StreetAddress {

    @Id
    private Long personId;

    private String address;
}
