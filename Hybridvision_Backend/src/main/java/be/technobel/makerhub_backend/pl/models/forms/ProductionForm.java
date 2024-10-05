package be.technobel.makerhub_backend.pl.models.forms;

import be.technobel.makerhub_backend.dal.models.enums.LicenseType;
import be.technobel.makerhub_backend.dal.models.enums.MusicGenre;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.message.Message;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ProductionForm {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "BPM is required")
    @Min(20) @Max(200)
    @Positive(message = "Number must be positive")
    private int bpm;
    private boolean available;
    private String stripePriceId;
    @FutureOrPresent
    private LocalDate releaseDate;
    @NotBlank(message = "Cover Image is required")
    private MultipartFile coverImage;
    @NotBlank(message = "Atleast one MP3 file is required")
    private MultipartFile audioMp3;
    private MultipartFile audioWav;
    private MultipartFile audioZip;
    private MusicGenre genre;
}
