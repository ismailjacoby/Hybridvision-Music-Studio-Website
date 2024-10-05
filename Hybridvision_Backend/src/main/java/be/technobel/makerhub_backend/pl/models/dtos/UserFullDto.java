package be.technobel.makerhub_backend.pl.models.dtos;

import be.technobel.makerhub_backend.dal.models.entities.UserEntity;

public record UserFullDto (String username,
                           String firstName,
                           String lastName,
                           String email,
                           boolean active,
                           boolean blocked) {

    public static UserFullDto fromDto(UserEntity user){
        return new UserFullDto(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isActive(),
                user.isBlocked()
        );
    }
}
