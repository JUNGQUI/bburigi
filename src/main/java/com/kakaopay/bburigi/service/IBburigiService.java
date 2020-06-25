package com.kakaopay.bburigi.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBburigiService {
    String createBburigi(long owner, String room, long price, long count);
}
