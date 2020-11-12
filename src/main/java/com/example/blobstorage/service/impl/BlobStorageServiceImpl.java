package com.example.blobstorage.service.impl;

import com.azure.storage.blob.models.ParallelTransferOptions;
import com.example.blobstorage.config.BlobServiceAsyncClientWrapper;
import com.example.blobstorage.model.BlobStorageResponse;
import com.example.blobstorage.service.BlobStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlobStorageServiceImpl implements BlobStorageService {

    private final BlobServiceAsyncClientWrapper asyncClientWrapper;

    @Override
    public Mono<BlobStorageResponse> getBlobFile(String blobName) {
        return asyncClientWrapper.getBlobAsyncClient(blobName)
                .flatMap(response -> {
                    //Agregar validacion cuando no existe el archivo
                    return Mono.fromCallable(() -> response);
                })
                .map(response -> {
                    BlobStorageResponse blobStorageResponse = new BlobStorageResponse();
                    blobStorageResponse.setBlobName(response.getBlobName());
                    blobStorageResponse.setBlobUrl(response.getBlobUrl());
                    return blobStorageResponse;
                });
    }

    @Override
    public Flux<BlobStorageResponse> uploadBlobFile(Flux<FilePart> filesPart) {
        return filesPart.flatMap(filePart -> asyncClientWrapper.getBlobAsyncClient(filePart.filename())
                .doOnSuccess(success -> log.info("Inicia carga de archivo"))
                .flatMap(blobAsyncClient -> blobAsyncClient.upload(
                        filePart.content().map(DataBuffer::asByteBuffer),
                        new ParallelTransferOptions(),
                        true))
                .doOnSuccess(success -> log.info("Se termino de subir archivo"))
                .map(response -> {
                    log.info("Se transforma la respuesta!!");
                    BlobStorageResponse blobStorageResponse = new BlobStorageResponse();
                    blobStorageResponse.setBlobUrl(response.getETag());
                    blobStorageResponse.setBlobName(response.getVersionId());
                    return blobStorageResponse;
                }))
                .onErrorResume(Mono::error);
    }
}
