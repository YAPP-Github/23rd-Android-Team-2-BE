package com.moneymong.global.image.api;

import com.moneymong.global.image.dto.ImageDeleteRequest;
import com.moneymong.global.image.dto.ImageResponse;
import com.moneymong.global.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "9. [이미지]")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "이미지 업로드")
    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE, APPLICATION_JSON_VALUE})
    public ImageResponse upload(@RequestPart("file") MultipartFile multipartFile) {
        ImageResponse response = imageService.upload(multipartFile);
        return response;
    }

    @Operation(summary = "이미지 삭제 API")
    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    public void remove(@RequestBody ImageDeleteRequest deleteRequest) {
        imageService.remove(deleteRequest);
    }
}
