package be.technobel.makerhub_backend.pl.models.dtos;

import be.technobel.makerhub_backend.dal.models.entities.UserEntity;


public record UserDto(
        String username,
        String firstName,
        String lastName,
        String email
) {
    public static UserDto fromDto(UserEntity user){
        return new UserDto(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
