
package org.example.people.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PeopleRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 250, message = "Name must not exceed 250 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Size(max = 250, message = "Email must not exceed 250 characters")
        @Email(message = "Please provide a valid email address")
        String email

) {
}

