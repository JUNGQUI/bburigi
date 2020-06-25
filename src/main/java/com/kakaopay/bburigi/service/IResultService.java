package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.BburigiResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IResultService {
    List<BburigiResult> createResult(long ownerUser, String room, long price, long count);
}
