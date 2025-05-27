package ru.urfu.scraperapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.neptunedata.model.S3Exception;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Тестовый контроллер для работы с S3 object storage")
public class S3Controller {

    private final S3Client s3Client;

    @Operation(summary = "Отправить файл с помощью form-data")
    @PostMapping("/upload-file")
    public boolean uploadFile(@RequestParam("file") MultipartFile file) {
        String bucketName = "bucket-for-virtualization-yandex";

        try {
            if (file.isEmpty()) {
                return false;
            }

            s3Client.putObject(request ->
                            request
                                    .bucket(bucketName)
                                    .key(file.getOriginalFilename())
                                    .contentType(file.getContentType())
                                    .contentLength(file.getSize()),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return true;
        } catch (IOException e) {
            log.error("Failed to upload file: {}", e.getMessage(), e);
            return false;
        } catch (S3Exception e) {
            log.error("S3 error during file upload: {}", e.getMessage(), e);
            return false;
        }
    }
}
