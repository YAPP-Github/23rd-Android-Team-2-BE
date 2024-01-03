package com.moneymong.global.image.service;

import com.moneymong.global.exception.enums.ErrorCode;
import com.moneymong.global.image.dto.ImageDeleteRequest;
import com.moneymong.global.image.exception.FileIOException;
import com.moneymong.global.image.exception.ImageNotExistsException;
import com.moneymong.global.image.handler.ImageStorageHandler;
import com.moneymong.global.image.dto.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageStorageHandler imageStorageHandler;

    /**
     * 파일에도 권한이 필요 할 수 있는데 그걸 고려 한 것 처럼 보이진 않아요.
     * 머니몽의 경우에는 해당 동아리나 관련 유저만 접근 가능 하도록 해야하는 사진들이 있을 수도 있을 것 같아요.
     */
    public ImageResponse upload(MultipartFile multipartFile, String dirName) {
        File file = convertMultipartFileToFile(multipartFile)
               .orElseThrow();

        return imageStorageHandler.upload(file, dirName);
    }

    public void remove(ImageDeleteRequest deleteRequest) {
        if (!imageStorageHandler.doesObjectExists(deleteRequest.getKey())) {
            throw new ImageNotExistsException(ErrorCode.IMAGE_NOT_EXISTS);
        }

        imageStorageHandler.remove(deleteRequest.getKey());
    }

    /**
     * 참고하면 좋을 글
     * https://techblog.woowahan.com/11392/
     */
    private Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        try {
            if (file.createNewFile()) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    fileOutputStream.write(multipartFile.getBytes());
                }
                return Optional.of(file);
            }

        } catch (IOException e) {
            throw new FileIOException(ErrorCode.FILE_IO_EXCEPTION);
        }
        return Optional.empty();
    }
}
