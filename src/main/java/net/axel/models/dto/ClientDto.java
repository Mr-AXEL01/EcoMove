package net.axel.models.dto;

public record ClientDto(
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
