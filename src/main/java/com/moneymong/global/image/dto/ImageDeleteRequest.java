package com.moneymong.global.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageDeleteRequest {
    private String key;
    private String path;
}
