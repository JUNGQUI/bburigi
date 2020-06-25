package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.BburigiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BburigiService implements IBburigiService {

    private final IResultService resultService;
    private final ISourceService sourceService;

    @Autowired
    public BburigiService(IResultService resultService, ISourceService sourceService) {
        this.resultService = resultService;
        this.sourceService = sourceService;
    }

    @Override
    public String createBburigi(long owner, String room, long price, long count) {
        List<BburigiResult> resultList = resultService.createResult(owner, price, count);

        String token = sourceService.createSource(owner, room, count, price, resultList);

        return token;
    }
}
