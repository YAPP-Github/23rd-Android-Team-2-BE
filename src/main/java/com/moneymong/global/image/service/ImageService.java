package com.moneymong.global.image.service;

import com.moneymong.global.exception.problem.ProblemParameters;
import com.moneymong.global.exception.problem.common.IOProblem;
import com.moneymong.global.image.dto.ImageDeleteRequest;
import com.moneymong.global.image.exception.FileNotFoundProblem;
import com.moneymong.global.image.exception.ImageNotExistsProblem;
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

    public ImageResponse upload(MultipartFile multipartFile, String dirName) {
        File file = convertMultipartFileToFile(multipartFile)
                .orElseThrow(() -> new FileNotFoundProblem(ProblemParameters.of("file", multipartFile.getName())));

        return imageStorageHandler.upload(file, dirName);
    }

    public void remove(ImageDeleteRequest deleteRequest) {
        if (!imageStorageHandler.doesObjectExists(deleteRequest.getKey())) {
            throw new ImageNotExistsProblem(ProblemParameters.of("key", deleteRequest.getKey()));
        }

        imageStorageHandler.remove(deleteRequest.getKey());
    }

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
            throw new IOProblem();
        }
        return Optional.empty();
    }
}
