package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.DTO.SourceDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBburigiService {
    String createBburigi(Long owner, String room, long price, long count);
}
