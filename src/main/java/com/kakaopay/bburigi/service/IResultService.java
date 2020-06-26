package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import com.kakaopay.bburigi.entity.DTO.ResultDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface IResultService {
    List<BburigiResult> createResult(String token, Long sourceUser, String room, long price, long count, Date date);

    ResultDTO getAvailableResult(String token, Long requestUser, String room, Date date);
}
