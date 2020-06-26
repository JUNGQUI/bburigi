package com.kakaopay.bburigi.service.repository;

import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import com.kakaopay.bburigi.entity.DAO.BburigiSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface IDynamicRepository {
    BburigiSource findAvailableSource(String token, long owner, Date date);

    List<BburigiResult> findAvailableResult(String token, Long requestUser, String room, Date date);
}
