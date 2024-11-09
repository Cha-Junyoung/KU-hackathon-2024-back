package com.example.KU_2024_hackathon.controller;

import com.example.KU_2024_hackathon.service.ShareService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/share")
@RequiredArgsConstructor
@Tag(name = "공유", description = "공유 관련 api입니다.")
public class ShareController
{
    private final ShareService shareService;
}
