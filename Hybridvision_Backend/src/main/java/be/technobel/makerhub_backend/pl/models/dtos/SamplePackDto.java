package be.technobel.makerhub_backend.pl.models.dtos;

import be.technobel.makerhub_backend.dal.models.entities.SamplePackEntity;
import jakarta.validation.constraints.NotBlank;

public record SamplePackDto(
       Long id,
       String title,
       String description,
       double price,
       String stripePriceId,
       String coverImageUrl,
       String audioUrl
) {

    public static SamplePackDto fromEntity(SamplePackEntity entity){
        return new SamplePackDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStripePriceId(),
                entity.getCoverImageUrl(),
                entity.getAudioUrl());
    }
}
