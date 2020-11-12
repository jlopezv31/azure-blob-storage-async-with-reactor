package com.example.blobstorage;

import com.example.blobstorage.config.BlobServiceAsyncClientWrapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/blob/example")
public class BlobStorageController {
    private final BlobServiceAsyncClientWrapper blobServiceAsyncClient;

    public BlobStorageController(BlobServiceAsyncClientWrapper blobServiceAsyncClient) {
        this.blobServiceAsyncClient = blobServiceAsyncClient;
    }

    @GetMapping(value = "/get", produces = {MediaType.APPLICATION_NDJSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Mono<String> getBlobUrl() {
        return blobServiceAsyncClient.getBlobFileUrl("business-ebpm-attachment_v2.xlsx");
    }
}


