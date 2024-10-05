package be.technobel.makerhub_backend.pl.models.dtos;

import be.technobel.makerhub_backend.dal.models.entities.ProductionEntity;
import be.technobel.makerhub_backend.dal.models.enums.LicenseType;
import be.technobel.makerhub_backend.dal.models.enums.MusicGenre;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ProductionDto(
Long id,
String title,
int bpm,
int duration,
LocalDate releaseDate,
String coverImage,
String audioMp3,
String audioWav,
String audioZip,
String stripePriceId,
MusicGenre genre
) {
    public static ProductionDto fromEntity(ProductionEntity p){
        return new ProductionDto(p.getId(),p.getTitle(),p.getBpm(),p.getDuration(),p.getReleaseDate(),p.getCoverImage(),p.getAudioMp3(),p.getAudioWav(),p.getAudioZip(),p.getStripePriceId(),p.getGenre());
    }
}
