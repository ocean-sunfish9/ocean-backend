package com.sparta.oceanbackend.domain.hotkeyword.repository;

import com.sparta.oceanbackend.domain.hotkeyword.entity.HotKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface HotKeywordRepository extends JpaRepository<HotKeyword, Long> {
  @Transactional
  @Modifying
  @Query("DELETE FROM HotKeyword")
  void deleteAllHotKeyword();
}
