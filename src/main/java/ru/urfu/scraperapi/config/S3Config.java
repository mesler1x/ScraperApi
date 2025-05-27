package ru.urfu.scraperapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        String ac = "YCAJEkJcR7sXpCCWUx0fRRviO";
        String sk = "YCOtaCWUHN_LpEkek-5hYYLzcWOS3yk53hsPTafh";
        AwsCredentials credentials = AwsBasicCredentials.create(ac, sk);
        return S3Client.builder()
                .endpointOverride(URI.create("https://storage.yandexcloud.net"))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
