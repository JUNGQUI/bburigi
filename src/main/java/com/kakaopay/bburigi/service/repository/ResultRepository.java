package com.kakaopay.bburigi.service.repository;

import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ResultRepository extends JpaRepository<BburigiResult, Long> {
    List<BburigiResult> findAllBySourceUserNotAndCommonInfo_RoomAndCommonInfo_OwnerUserNotAndCommonInfo_ExpireAfter();
}
