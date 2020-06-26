package com.kakaopay.bburigi.service.repository;

import com.kakaopay.bburigi.entity.DAO.BburigiSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
public interface SourceRepository extends JpaRepository<BburigiSource, Long> {

    @Query("select source " +
            "from BburigiSource source " +
            "where source.token = :token and source.commonInfo.ownerUser = :owner and source.commonInfo.expire >= :expire")
    BburigiSource searchMyBburigi(@Param("token") String token, @Param("owner") Long owner, @Param("expire") Date expire);
    BburigiSource findByToken(String token);
}
