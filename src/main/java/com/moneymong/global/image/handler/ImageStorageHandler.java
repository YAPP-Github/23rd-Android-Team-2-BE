package com.moneymong.global.image.handler;

import com.moneymong.global.image.dto.ImageResponse;

import java.io.File;

public interface ImageStorageHandler {

    ImageResponse upload(File file, String dirName);

    void remove(String key);

    boolean doesObjectExists(String key);
}
