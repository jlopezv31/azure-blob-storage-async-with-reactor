package com.example.blobstorage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class BlobStorageProperties {

    @Value("${azure-connection.account}")
    private String accountName;

    @Value("${azure-connection.key}")
    private String accountKey;

    @Value("${azure-connection.container}")
    private String containerName;

    @Value("${azure-connection.endpoint}")
    private String endpoint;
}
