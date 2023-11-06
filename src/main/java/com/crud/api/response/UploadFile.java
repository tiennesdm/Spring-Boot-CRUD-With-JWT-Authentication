package com.crud.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UploadFile {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
