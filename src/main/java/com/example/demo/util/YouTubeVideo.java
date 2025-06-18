package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YouTubeVideo {
    private String videoId;
    private String title;
    private String thumbnailUrl;
}
