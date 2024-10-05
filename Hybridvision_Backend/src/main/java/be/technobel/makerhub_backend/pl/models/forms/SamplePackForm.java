package be.technobel.makerhub_backend.pl.models.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SamplePackForm {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Price is required")
    @Positive(message = "Number must be positive")
    private double price;
    private String stripePriceId;
    @NotBlank(message = "Image is required")
    private String coverImageUrl;
    @NotBlank(message = "Audio File is required")
    private String audioUrl;
}
