package be.technobel.makerhub_backend.bll.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;
import software.amazon.awssdk.services.s3.S3Client;



@Configuration
public class S3Config {
    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKeyId;
    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretAccessKey;
    @Value("${spring.cloud.aws.region.static}")
    private String awsRegion;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId,secretAccessKey)))
                .region(Region.EU_WEST_3)
                .build();
    }

    @Bean
    public AwsRegionProvider awsRegionProvider(){
        return () -> Region.of((awsRegion));
    }
}
