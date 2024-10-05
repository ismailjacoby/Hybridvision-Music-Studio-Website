package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.bll.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements FileService {

    private final S3Client s3Client;
    private final String bucketName = "hybridvision";

    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(MultipartFile file){
        //Get Filename Extension
        String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        //Creates a random UUID
        String key = UUID.randomUUID().toString();
        if(StringUtils.hasLength(filenameExtension)) {
            key += "." + filenameExtension;
        }

        try{
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
        } catch (IOException ioException){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while uploading the file.");
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            String key = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "AWS service error during file deletion", e);
        } catch (SdkClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "AWS SDK client error during file deletion", e);
        }
    }
}
