package com.familring.calendarservice.service.client;

import com.familring.common_service.dto.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "file-service")
public interface FileServiceFeignClient {
    @PostMapping(value = "/client/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BaseResponse<List<String>> uploadFiles(
            @RequestPart("files") List<MultipartFile> files,
            @RequestParam(value = "folderPath", required = false) String folderPath
    );

    @DeleteMapping("/client/files")
    BaseResponse<Void> deleteFiles(@RequestBody List<String> fileUrls);
}
