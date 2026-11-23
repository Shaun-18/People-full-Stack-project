package org.example.people.Service;


import org.example.people.Entity.People;
import org.springframework.stereotype.Service;
import org.example.people.DTO.PeopleRequest;
import org.example.people.DTO.PeopleResponse;
import org.example.people.Repository.PeopleRepository;
import org.example.people.Exceptions.DuplicateResourceException;
import org.example.people.Exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    // Create Person
    @Transactional
    public PeopleResponse createPerson(PeopleRequest request) {

        if (peopleRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        People people = new People();

        people.setName(request.name());
        people.setEmail(request.email());


        People savedPeople = peopleRepository.save(people);

        return mapToResponse(savedPeople);
    }

    // Read All People
    @Transactional(readOnly = true)
    public List<PeopleResponse> getAllPeople() {

        return peopleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Read One Person
    @Transactional(readOnly = true)
    public PeopleResponse getPeopleById(long id) {

        People people = findPersonOrThrow(id);

        return mapToResponse(people);
    }

    // Update Person
    @Transactional
    public PeopleResponse updatePeople(long id, PeopleRequest request) {

        People people = findPersonOrThrow(id);

        boolean personAlreadyExists =
                peopleRepository.existsByEmailAndIdNot(
                        request.email(), id
                );

        if (personAlreadyExists) {
            throw new DuplicateResourceException(
                    "Person with this email already exists"
            );
        }

        people.setName(request.name());
        people.setEmail(request.email());

        People updatedPeople = peopleRepository.save(people);

        return mapToResponse(updatedPeople);
    }

    // Delete Person
    @Transactional
    public void deletePeople(long id) {

        People people = findPersonOrThrow(id);

        peopleRepository.delete(people);
    }

    // Find Person or Throw Exception
    private People findPersonOrThrow(long id) {

        return peopleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Person with ID " + id + " not found"
                ));
    }

    // Map Entity to Response DTO
    private PeopleResponse mapToResponse(People people) {

        return new PeopleResponse(
                people.getId(),
                people.getName(),
                people.getEmail()
        );
    }
}
