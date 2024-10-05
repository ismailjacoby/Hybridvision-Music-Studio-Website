package be.technobel.makerhub_backend.bll.services;

import org.springframework.web.multipart.MultipartFile;

public interface  FileService {
    String uploadFile(MultipartFile file);
    void deleteFile(String fileUrl);
}
