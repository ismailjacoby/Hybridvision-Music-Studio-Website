package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.bll.exceptions.NotFoundException;
import be.technobel.makerhub_backend.bll.services.SamplePackService;
import be.technobel.makerhub_backend.dal.models.entities.ProductionEntity;
import be.technobel.makerhub_backend.dal.models.entities.SamplePackEntity;
import be.technobel.makerhub_backend.dal.models.enums.MusicGenre;
import be.technobel.makerhub_backend.dal.repositories.SamplePackRepository;
import be.technobel.makerhub_backend.pl.models.forms.ProductionForm;
import be.technobel.makerhub_backend.pl.models.forms.SamplePackForm;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class SamplePackImpl implements SamplePackService {
    private final S3ServiceImpl s3Service;
    private final SamplePackRepository samplePackRepository;

    public SamplePackImpl(S3ServiceImpl s3Service,
                          SamplePackRepository samplePackRepository) {
        this.s3Service = s3Service;

        this.samplePackRepository = samplePackRepository;
    }

    @Override
    public void uploadProduction(String title, String description, double price, String stripePriceId, MultipartFile coverImageUrl, MultipartFile audioUrl) {
        SamplePackEntity samplePack = new SamplePackEntity();

        samplePack.setTitle(title);
        samplePack.setDescription(description);
        samplePack.setPrice(price);
        samplePack.setStripePriceId(stripePriceId);


        if (!audioUrl.isEmpty()) {
            String mp3Url = s3Service.uploadFile(audioUrl);
            samplePack.setAudioUrl(mp3Url);
        }
        if (!coverImageUrl.isEmpty()) {
            samplePack.setCoverImageUrl(s3Service.uploadFile(coverImageUrl));
        }

        samplePackRepository.save(samplePack);
    }

    @Override
    public void editSamplePack(SamplePackForm samplePackForm, Long id) {
        SamplePackEntity samplePack = samplePackRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Can't find sample pack with id"));

        if(samplePackForm == null){
            throw new IllegalArgumentException("Form can't be null");
        }

        samplePack.setTitle(samplePackForm.getTitle());
        samplePack.setDescription(samplePackForm.getDescription());
        samplePack.setPrice(samplePackForm.getPrice());
        samplePack.setCoverImageUrl(String.valueOf(samplePackForm.getCoverImageUrl()));
        samplePack.setAudioUrl(String.valueOf(samplePackForm.getAudioUrl()));

        samplePackRepository.save(samplePack);
    }

    @Override
    public Optional<SamplePackEntity> getSamplePackById(Long id) {
        return samplePackRepository.findById(id);
    }

    @Override
    public List<SamplePackEntity> getAllSamplePacks() {
        return samplePackRepository.findAll();
    }

    @Override
    public void deleteSamplePack(Long id) {
        SamplePackEntity samplePack = samplePackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find Sample Pack with id: " + id));

        if (samplePack.getCoverImageUrl() != null) {
            s3Service.deleteFile(samplePack.getCoverImageUrl());
        }

        if (samplePack.getAudioUrl() != null) {
            s3Service.deleteFile(samplePack.getAudioUrl());
        }

        samplePackRepository.deleteById(id);
    }
}
