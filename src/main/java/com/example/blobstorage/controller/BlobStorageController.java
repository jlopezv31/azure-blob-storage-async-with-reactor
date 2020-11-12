package com.example.blobstorage.controller;

import com.example.blobstorage.model.BlobStorageResponse;
import com.example.blobstorage.service.BlobStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/blob/example")
public class BlobStorageController {

    private final BlobStorageService blobStorageService;

    public BlobStorageController(BlobStorageService blobStorageService) {
        this.blobStorageService = blobStorageService;
    }

    @GetMapping(value = "/get", produces = {MediaType.APPLICATION_NDJSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Mono<BlobStorageResponse> getBlobUrl(@RequestParam("fileName") String blobName) {
        return blobStorageService.getBlobFile(blobName);
    }

    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<List<BlobStorageResponse>> uploadBlob(
            @RequestPart("files") Flux<FilePart> filesPart) {
        return blobStorageService.uploadBlobFile(filesPart).collectList();
    }
}


