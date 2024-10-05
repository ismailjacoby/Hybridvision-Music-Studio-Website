package be.technobel.makerhub_backend.bll.services;

import be.technobel.makerhub_backend.dal.models.entities.ProductionEntity;
import be.technobel.makerhub_backend.pl.models.forms.ProductionForm;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductionService {
    void uploadProduction(String title, int bpm, LocalDate releaseDate, String genre,String stripePriceId, MultipartFile coverImage, MultipartFile audioMp3, MultipartFile audioWav, MultipartFile audioZip);
    void editProduction(ProductionForm productionForm, Long id);
    Optional<ProductionEntity> getProductionById(Long id);
    List<ProductionEntity> getAllProductions();

    void deleteProduction(Long id);

}
