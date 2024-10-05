package be.technobel.makerhub_backend.bll.services;

import be.technobel.makerhub_backend.dal.models.entities.SamplePackEntity;
import be.technobel.makerhub_backend.pl.models.forms.ProductionForm;
import be.technobel.makerhub_backend.pl.models.forms.SamplePackForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface SamplePackService {
    void uploadProduction(String title, String description, double price, String stripePriceId, MultipartFile coverImage, MultipartFile audioUrl);
    void editSamplePack(SamplePackForm samplePackForm, Long id);
    Optional<SamplePackEntity> getSamplePackById(Long id);
    List<SamplePackEntity> getAllSamplePacks();
    void deleteSamplePack(Long id);
}
