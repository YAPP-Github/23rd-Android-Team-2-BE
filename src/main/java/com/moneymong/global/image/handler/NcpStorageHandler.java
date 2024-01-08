package com.moneymong.global.image.handler;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.moneymong.global.image.dto.ImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NcpStorageHandler implements ImageStorageHandler{
    private static final String ROOT_DIRNAME = "dev";

    private final AmazonS3 amazonS3;

    @Value("${cloud.ncp.s3.bucket}")
    private String bucket;

    @Override
    public ImageResponse upload(File file) {
        String key = generateRandomFileName(file);
        String path = putImage(file, key);
        removeFile(file);

        return new ImageResponse(key, path);
    }

    private void removeFile(File file) {
        file.delete();
    }

    @Override
    public void remove(String key) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        amazonS3.deleteObject(deleteObjectRequest);
    }

    @Override
    public boolean doesObjectExists(String key) {
        return amazonS3.doesObjectExist(bucket, key);
    }

    private String generateRandomFileName(File file) {
        return ROOT_DIRNAME + "/" + UUID.randomUUID().toString().substring(0, 8) + "-" + file.getName();
    }

    private String putImage(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return getImagePath(bucket, fileName);
    }

    private String getImagePath(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
