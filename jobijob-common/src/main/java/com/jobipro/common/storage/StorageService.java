package com.jobipro.common.storage;

import com.jobipro.common.dto.storage.ObjectMetadata;
import com.jobipro.common.exceptions.StorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;


@Service
@RequiredArgsConstructor
public class StorageService implements StorageServiceContract {

    private final S3Presigner s3Presigner;
    private final S3Client s3Client;


    @Value("${storage.bucket}")
    private String bucket;

    @Override
    public String generateUploadUrl(String objectKey, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(5))
                        .putObjectRequest(putObjectRequest)
                        .build();

        return s3Presigner
                .presignPutObject(presignRequest)
                .url()
                .toString();
    }

    @Override
    public String generateDownloadUrl(String objectKey) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(30))
                        .getObjectRequest(getObjectRequest)
                        .build();

        return s3Presigner
                .presignGetObject(presignRequest)
                .url()
                .toString();
    }

    @Override
    public ObjectMetadata getObjectMetadata(String objectKey) {

        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .build();

            HeadObjectResponse response = s3Client.headObject(request);

            return new ObjectMetadata(
                    response.contentLength(),
                    response.contentType()
            );

        } catch (S3Exception e) {
            throw new StorageException(
                    "Unable to retrieve object metadata: " + objectKey,
                    e
            );
        }
    }
}
