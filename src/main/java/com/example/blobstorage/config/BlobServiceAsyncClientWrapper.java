package com.example.blobstorage.config;

import com.azure.storage.blob.BlobAsyncClient;
import com.azure.storage.blob.BlobServiceAsyncClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BlobServiceAsyncClientWrapper {

    private final BlobServiceAsyncClient blobServiceAsyncClient;
    private final BlobStorageProperties properties;


    public BlobServiceAsyncClientWrapper(BlobStorageProperties properties
    ) {
        this.blobServiceAsyncClient = new BlobServiceClientBuilder()
                .endpoint(properties.getEndpoint())
                .credential(new StorageSharedKeyCredential(properties.getAccountName(),
                        properties.getAccountKey()))
                .buildAsyncClient();
        this.properties = properties;
    }

    public Mono<BlobAsyncClient> getBlobAsyncClient(String blobName) {
        return Mono.fromCallable(() ->
                blobServiceAsyncClient.getBlobContainerAsyncClient(properties.getContainerName())
                        .getBlobAsyncClient(blobName));
    }
}
