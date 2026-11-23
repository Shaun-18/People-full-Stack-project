package org.example.people.Repository;

import org.example.people.Entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<People, Long> {
    boolean existsByID(String id);

    boolean existsByEmailAndEmailNot(String email, Long id);

    Optional<People>findbyemail(String email);
}
