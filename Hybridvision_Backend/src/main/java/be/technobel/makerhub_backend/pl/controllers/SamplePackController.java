package be.technobel.makerhub_backend.pl.controllers;

import be.technobel.makerhub_backend.bll.exceptions.NotFoundException;
import be.technobel.makerhub_backend.bll.services.SamplePackService;
import be.technobel.makerhub_backend.pl.models.dtos.ProductionDto;
import be.technobel.makerhub_backend.pl.models.dtos.SamplePackDto;
import be.technobel.makerhub_backend.pl.models.forms.ProductionForm;
import be.technobel.makerhub_backend.pl.models.forms.SamplePackForm;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/samplepack")
public class SamplePackController {

    private final SamplePackService samplePackService;

    public SamplePackController(SamplePackService samplePackService) {
        this.samplePackService = samplePackService;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> uploadSamplePack(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam(value = "stripePriceId", required = false) String stripePriceId,
            @RequestParam("coverImage") MultipartFile coverImage,
            @RequestParam("audioUrl") MultipartFile audioUrl) {
        samplePackService.uploadProduction(title, description, price, stripePriceId,coverImage, audioUrl);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public void editSamplePack(@RequestBody SamplePackForm samplePackForm, Long id){
        samplePackService.editSamplePack(samplePackForm,id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SamplePackDto> getSamplePackById(@PathVariable Long id) {
        return ResponseEntity.ok(SamplePackDto.fromEntity(samplePackService.getSamplePackById(id)
                .orElseThrow(()-> new NotFoundException("Production not found"))));
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SamplePackDto>> getAllProductions() {
        List<SamplePackDto> samplepack = samplePackService.getAllSamplePacks()
                .stream()
                .map(SamplePackDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(samplepack);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduction(@PathVariable Long id) {
        samplePackService.deleteSamplePack(id);
        return ResponseEntity.ok().build();
    }

}








