package com.supv.challenge.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.supv.challenge.domain.Person;

/**
 * Spring Data repository for the Person entity.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
