package com.moneymong.global.image.api;

import com.moneymong.global.image.dto.ImageDeleteRequest;
import com.moneymong.global.image.dto.ImageResponse;
import com.moneymong.global.image.service.ImageService;
import com.moneymong.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE, APPLICATION_JSON_VALUE})
    public ApiResponse<ImageResponse> upload(@RequestPart("file") MultipartFile multipartFile, @ModelAttribute("dirName") String dirName) {
        ImageResponse response = imageService.upload(multipartFile, dirName);
        return ApiResponse.success(response);
    }

    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    public ApiResponse<Void> remove(@RequestBody ImageDeleteRequest deleteRequest) {
        imageService.remove(deleteRequest);
        return ApiResponse.success();
    }
}
