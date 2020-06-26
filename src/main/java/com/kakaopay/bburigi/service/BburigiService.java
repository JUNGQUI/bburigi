package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import com.kakaopay.bburigi.entity.DTO.SourceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

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
    public String createBburigi(Long owner, String room, long price, long count) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> task = sourceService::getUniqueToken;
        Future<String> future = executorService.submit(task);

        try {
            // token 발급은 최대 3초
            future.get(3000, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            return null;
        }

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
