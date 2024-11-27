package com.sparta.oceanbackend.api.hotkeyword.controller;

import com.sparta.oceanbackend.api.hotkeyword.dto.HotKeywordReadResponse;
import com.sparta.oceanbackend.api.hotkeyword.service.HotKeywordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotkeywords")
public class HotKeywordController {

    private final HotKeywordService hotKeywordService;

    @GetMapping("/v1")
    public ResponseEntity<List<HotKeywordReadResponse>> getHotKeyword(){
        return ResponseEntity.status(HttpStatus.OK).body(hotKeywordService.getHotKeyword());
    }

    @GetMapping("/v2")
    public ResponseEntity<List<HotKeywordReadResponse>> getHotKeywordInMemory() {
        return ResponseEntity.status(HttpStatus.OK).body(hotKeywordService.getHotKeywordInMemory());
    }

    @GetMapping("/v3")
    public ResponseEntity<List<HotKeywordReadResponse>> getHotKeywordRedis() {
        return ResponseEntity.status(HttpStatus.OK).body(hotKeywordService.getHotKeywordRedis());
    }
}
