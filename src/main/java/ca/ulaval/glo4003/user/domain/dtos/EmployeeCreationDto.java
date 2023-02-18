package ca.ulaval.glo4003.user.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeCreationDto(
        @JsonProperty("userId") String userId,
        @JsonProperty("name") String name,
        @JsonProperty("birthDate") String birthDate,
        @JsonProperty("gender") String gender,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("role") String role
) {
}
