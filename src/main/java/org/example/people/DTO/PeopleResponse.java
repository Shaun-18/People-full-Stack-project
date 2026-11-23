package org.example.people.DTO;

public record PeopleResponse (
        Long id,
        String name,
        String email
) {
}
