package com.kakaopay.bburigi.service.repository;

import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface ResultRepository extends JpaRepository<BburigiResult, Long> {

    @Query("select result " +
            "from BburigiResult result " +
            "where result.commonInfo.ownerUser = :requestUser and result.commonInfo.expire >= :requestedDate")
    BburigiResult searchRequestedResult(@Param("requestUser") Long requestUser, @Param("requestedDate") Date requestedDate);

    @Query("select result " +
            "from BburigiResult result " +
            "where result.token = :token " +
            "and result.sourceUser <> :requestedUser " +
            "and result.commonInfo.room = :room " +
            "and result.commonInfo.ownerUser = -1L " +
            "and result.commonInfo.expire >= :requestedDate")
    List<BburigiResult> searchNotPaidResult(
            @Param("token") String token, @Param("requestedUser") Long sourceUser, @Param("room") String room, @Param("requestedDate") Date date);

}
