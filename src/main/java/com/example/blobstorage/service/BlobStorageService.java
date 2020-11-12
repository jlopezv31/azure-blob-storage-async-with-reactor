package com.example.blobstorage.service;

import com.example.blobstorage.model.BlobStorageResponse;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlobStorageService {
    Mono<BlobStorageResponse> getBlobFile(String blobName);

    Flux<BlobStorageResponse> uploadBlobFile(Flux<FilePart> filesPart);
}
