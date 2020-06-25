package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.BburigiResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ISourceService {

    String createSource(long owner, String room, long price, long count, List<BburigiResult> resultList);

    String getUniqueToken();
}
