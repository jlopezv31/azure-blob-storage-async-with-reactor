package com.example.blobstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlobStorageResponse implements Serializable {
    private static final long serialVersionUID = -4394477487238199744L;

    private String blobName;
    private String blobUrl;
}
