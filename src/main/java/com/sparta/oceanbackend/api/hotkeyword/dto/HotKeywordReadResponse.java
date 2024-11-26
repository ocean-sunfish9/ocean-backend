package com.sparta.oceanbackend.api.hotkeyword.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HotKeywordReadResponse {
  private String keyword;

  @Builder
  public HotKeywordReadResponse(String keyword) {
    this.keyword = keyword;
  }
}
