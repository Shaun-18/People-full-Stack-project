package org.example.people.Controller;


import jakarta.validation.Valid;
import org.example.people.DTO.PeopleRequest;
import org.example.people.DTO.PeopleResponse;
import org.example.people.Service.PeopleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/People")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    //Create Person
    @PostMapping
    public ResponseEntity<PeopleResponse> createPerson(
            @Valid @RequestBody PeopleRequest request
            ){
        PeopleResponse response = peopleService.createPerson(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Get All People
    @GetMapping
    public ResponseEntity<List<PeopleResponse>>getAllPeople(){
        return ResponseEntity.ok(peopleService.getAllPeople());
    }

    //Get People by ID
    @GetMapping("/{id}")
    public ResponseEntity<PeopleResponse>getPeopleByID(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(peopleService.getPeopleById(id));

    }
    //Update People
    @PutMapping("/{id}")
    public ResponseEntity<PeopleResponse>updatePerson(
            @PathVariable Long id,
            @Valid @RequestBody PeopleRequest request
    ){
        return ResponseEntity.ok(peopleService.updatePeople(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletePerson(
            @PathVariable long id
    ){
        peopleService.deletePeople(id);
        return ResponseEntity.noContent().build();
    }
}
