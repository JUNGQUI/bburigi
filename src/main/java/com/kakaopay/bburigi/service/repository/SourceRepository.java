package com.kakaopay.bburigi.service.repository;

import com.kakaopay.bburigi.entity.DAO.BburigiSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface SourceRepository extends JpaRepository<BburigiSource, Long> {
    @Transactional
    BburigiSource findByTokenAndCommonInfo_OwnerUserAndCommonInfo_ExpireAfter(String token, long owner, Date expire);

    @Transactional
    BburigiSource findByToken(String token);
}
