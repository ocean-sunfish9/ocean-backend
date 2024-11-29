package com.sparta.oceanbackend.api.hotkeyword.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class HotKeywordReadResponse implements Serializable {
  private static final long serialVersionUID = 1L; // 직렬화 명시적으로 정의

  private String keyword;

  @Builder
  public HotKeywordReadResponse(String keyword) {
    this.keyword = keyword;
  }
}
