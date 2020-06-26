package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
        String token = sourceService.getUniqueToken();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        List<BburigiResult> resultList = resultService.createResult(token, owner, room, price, count, calendar.getTime());

        calendar.add(Calendar.MINUTE, -10);
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        sourceService.createSource(token, owner, room, price, count, calendar.getTime(), resultList);

        return token;
    }
}
