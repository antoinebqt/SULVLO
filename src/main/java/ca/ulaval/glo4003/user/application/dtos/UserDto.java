package ca.ulaval.glo4003.user.application.dtos;

public record UserDto(String userId,
                      String name,
                      String birthDate,
                      String gender,
                      String emailAddress,
                      String role) {
}
