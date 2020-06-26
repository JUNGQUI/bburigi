package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import com.kakaopay.bburigi.entity.DAO.BburigiSource;
import com.kakaopay.bburigi.entity.DTO.SourceDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface ISourceService {

    void createSource(String token, Long owner, String room, long price, long count, Date expire,
                        List<BburigiResult> resultList);

    String getUniqueToken();

    SourceDTO getSource(String token, Long owner);
}
