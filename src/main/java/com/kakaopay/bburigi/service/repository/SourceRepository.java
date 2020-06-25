package com.kakaopay.bburigi.service.repository;

import com.kakaopay.bburigi.entity.BburigiSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SourceRepository extends JpaRepository<BburigiSource, Long> {
    @Transactional
    BburigiSource findByToken(String token);
}
