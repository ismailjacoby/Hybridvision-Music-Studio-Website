package be.technobel.makerhub_backend.pl.models.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EmailForm {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
