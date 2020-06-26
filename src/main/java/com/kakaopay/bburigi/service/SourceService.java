package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.DAO.BburigiCommonInfo;
import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import com.kakaopay.bburigi.entity.DAO.BburigiSource;
import com.kakaopay.bburigi.entity.DTO.ResultDTO;
import com.kakaopay.bburigi.entity.DTO.SourceDTO;
import com.kakaopay.bburigi.service.repository.IDynamicRepository;
import com.kakaopay.bburigi.service.repository.SourceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Service
class SourceService implements ISourceService {

    private final SourceRepository sourceRepository;
    private final IDynamicRepository dynamicRepository;

    @Autowired
    public SourceService(SourceRepository sourceRepository, IDynamicRepository dynamicRepository) {
        this.sourceRepository = sourceRepository;
        this.dynamicRepository = dynamicRepository;
    }

    @Override
    public void createSource(String token, Long owner, String room, long price, long count, Date expire,
                               List<BburigiResult> resultList) {

        Assert.isTrue(resultList != null && resultList.size() > 0, "뿌리기 생성 실패, 분배 금액이 없습니다.");

        BburigiCommonInfo commonInfo = new BburigiCommonInfo(owner, room, price, expire);
        BburigiSource source = new BburigiSource(token, count, commonInfo, resultList);

        sourceRepository.save(source);
    }

    @Override
    public String getUniqueToken() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> task = this::searchUniqueToken;
        Future<String> future = executorService.submit(task);

        String token;

        try {
            // token 발급은 최대 3초
            token = future.get(3000, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            return null;
        }

        return token;
    }

    @Override
    public SourceDTO getSource(String token, Long owner) {
        BburigiSource bburigiSource = dynamicRepository.findAvailableSource(token, owner, new Date());

        if (bburigiSource == null) {
            return null;
        }

        List<ResultDTO> resultDTOList = new ArrayList<>();

        long paidPrice = 0L;

        for (BburigiResult resultDAO : bburigiSource.getResult()) {
            if (resultDAO.getCommonInfo().getOwnerUser() != -1L) {
                ResultDTO resultDTO = new ResultDTO(
                        resultDAO.getCommonInfo().getPrice(),
                        resultDAO.getCommonInfo().getOwnerUser()
                );

                paidPrice += resultDAO.getCommonInfo().getPrice();
                resultDTOList.add(resultDTO);
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bburigiSource.getCommonInfo().getExpire());
        calendar.add(Calendar.DAY_OF_MONTH, -7);

        return new SourceDTO(calendar.getTime(), bburigiSource.getCommonInfo().getPrice(), paidPrice, resultDTOList);
    }

    private String searchUniqueToken() {
        String token;

        do {
            token = RandomStringUtils.random(3, true, true);
        } while (sourceRepository.findByToken(token) != null);

        // method timeout test code
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return token;
    }
}
