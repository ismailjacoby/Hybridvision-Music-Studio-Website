package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.bll.exceptions.NotFoundException;
import be.technobel.makerhub_backend.bll.services.ProductionService;
import be.technobel.makerhub_backend.dal.models.entities.ProductionEntity;
import be.technobel.makerhub_backend.dal.models.enums.MusicGenre;
import be.technobel.makerhub_backend.dal.repositories.ProductionRepository;
import be.technobel.makerhub_backend.pl.models.forms.ProductionForm;
import com.mpatric.mp3agic.Mp3File;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductionServiceImpl implements ProductionService {

    private final S3ServiceImpl s3Service;
    private final ProductionRepository productionRepository;

    public ProductionServiceImpl(S3ServiceImpl s3Service, ProductionRepository productionRepository) {
        this.s3Service = s3Service;
        this.productionRepository = productionRepository;
    }

    @Override
    public void uploadProduction(String title, int bpm, LocalDate releaseDate, String genre,String stripePriceId, MultipartFile coverImage, MultipartFile audioMp3, MultipartFile audioWav, MultipartFile audioZip){
        ProductionEntity production = new ProductionEntity();

        production.setTitle(title);
        production.setBpm(bpm);
        production.setReleaseDate(releaseDate);
        production.setGenre(MusicGenre.valueOf(genre));
        production.setAvailable(true);
        production.setStripePriceId(stripePriceId);

        if (!audioMp3.isEmpty()) {
            String mp3Url = s3Service.uploadFile(audioMp3);
            production.setAudioMp3(mp3Url);
            production.setDuration(extractDuration(audioMp3));
        }
        if (audioWav != null && !audioWav.isEmpty()) {
            production.setAudioWav(s3Service.uploadFile(audioWav));
        }
        if (audioZip != null && !audioZip.isEmpty()) {
            production.setAudioZip(s3Service.uploadFile(audioZip));
        }
        if (!coverImage.isEmpty()) {
            production.setCoverImage(s3Service.uploadFile(coverImage));
        }

        productionRepository.save(production);
    }

    @Override
    public void editProduction(ProductionForm productionForm, Long id) {
        ProductionEntity savedProduction = productionRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Can't find production with id: " + productionForm.getId()));

        if(productionForm == null){
            throw new IllegalArgumentException("Form can't be null");
        }

        savedProduction.setTitle(productionForm.getTitle());
        savedProduction.setBpm(productionForm.getBpm());
        savedProduction.setReleaseDate(productionForm.getReleaseDate());
        savedProduction.setAvailable(productionForm.isAvailable());
        savedProduction.setAudioMp3(String.valueOf(productionForm.getAudioMp3()));
        savedProduction.setAudioWav(String.valueOf(productionForm.getAudioWav()));
        savedProduction.setAudioZip(String.valueOf(productionForm.getAudioZip()));
        savedProduction.setCoverImage(String.valueOf(productionForm.getCoverImage()));
        savedProduction.setGenre(productionForm.getGenre());
        savedProduction.setStripePriceId(productionForm.getStripePriceId());

        productionRepository.save(savedProduction);
    }

    @Override
    public Optional<ProductionEntity> getProductionById(Long id) {
        return productionRepository.findById(id);
    }

    @Override
    public List<ProductionEntity> getAllProductions() {
        return productionRepository.findAll();
    }

    @Override
    public void deleteProduction(Long id) {
        ProductionEntity production = productionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find production with id: " + id));

        if (production.getCoverImage() != null) {
            s3Service.deleteFile(production.getCoverImage());
        }
        if (production.getAudioMp3() != null) {
            s3Service.deleteFile(production.getAudioMp3());
        }
        if (production.getAudioWav() != null) {
            s3Service.deleteFile(production.getAudioWav());
        }
        if (production.getAudioZip() != null) {
            s3Service.deleteFile(production.getAudioZip());
        }

        productionRepository.deleteById(id);
    }

    private int extractDuration(MultipartFile file) {
        try {
            // Temporary file creation
            Path tempPath = Files.createTempFile("upload", ".mp3");
            File tempFile = tempPath.toFile();
            file.transferTo(tempFile);

            // Mp3File instance for reading properties
            Mp3File mp3File = new Mp3File(tempFile);
            int durationInSeconds = (int) mp3File.getLengthInSeconds();

            // Cleanup
            Files.delete(tempPath);

            return durationInSeconds;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to extract MP3 duration", e);
        }
    }
}
