package com.bmachine.url_shorter_service.infrastructure.repository;

import com.bmachine.url_shorter_service.infrastructure.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaUrlRepository extends JpaRepository<UrlEntity, Long> {
    List<UrlEntity> findByShortCode(String shortCode);
}
