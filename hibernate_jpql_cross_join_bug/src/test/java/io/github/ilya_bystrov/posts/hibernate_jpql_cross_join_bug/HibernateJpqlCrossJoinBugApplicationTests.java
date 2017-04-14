package io.github.ilya_bystrov.posts.hibernate_jpql_cross_join_bug;

import io.github.ilya_bystrov.posts.hibernate_jpql_cross_join_bug.domain.entity.Person;
import io.github.ilya_bystrov.posts.hibernate_jpql_cross_join_bug.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HibernateJpqlCrossJoinBugApplicationTests {

    @Autowired
    private PersonRepository personRepository;

	@Test
	public void explicitJoin() {
        List<Person> persons = personRepository.findAllByStreetAddressLikeExplicit("842 Fremont Plaza");
        log.info(persons.toString());
    }

    @Test
    public void implicitJoin() {
        List<Person> persons = personRepository.findAllByStreetAddressLikeImplicit("842 Fremont Plaza");
        log.info(persons.toString());
    }
}
