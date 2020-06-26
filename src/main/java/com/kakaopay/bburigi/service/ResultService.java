package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.DAO.BburigiCommonInfo;
import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import com.kakaopay.bburigi.entity.DTO.ResultDTO;
import com.kakaopay.bburigi.service.repository.IDynamicRepository;
import com.kakaopay.bburigi.service.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResultService implements IResultService {

    private final ResultRepository resultRepository;
    private final IDynamicRepository dynamicRepository;

    @Autowired
    public ResultService(ResultRepository resultRepository, IDynamicRepository dynamicRepository) {
        this.resultRepository = resultRepository;
        this.dynamicRepository = dynamicRepository;
    }

    @Override
    public List<BburigiResult> createResult(String token, Long sourceUser, String room, long price, long count, Date date) {
        List<BburigiResult> resultList = new ArrayList<>();

        long remainPrice = price;

        for (long i = 0L; i < count; i++) {
            long resultPrice = this.calculate(count, remainPrice);

            remainPrice -= resultPrice;

            if (i+1 == count) {
                resultPrice += remainPrice;
            }

            BburigiCommonInfo commonInfo = new BburigiCommonInfo(-1L, room, resultPrice, date);

            BburigiResult result = new BburigiResult(token, sourceUser, commonInfo);

            resultRepository.save(result);

            resultList.add(result);
        }

        return resultList;
    }

    @Override
    public ResultDTO getAvailableResult(String token, Long requestUser, String room, Date date) {
        List<BburigiResult> resultList = dynamicRepository.findAvailableResult(token, requestUser, room, date);
        ResultDTO resultDTO = null;

        for (BburigiResult resultDAO : resultList) {
            resultDTO = new ResultDTO(resultDAO.getCommonInfo().getPrice(), requestUser);

            BburigiCommonInfo commonInfo = resultDAO.getCommonInfo();
            commonInfo.setOwnerUser(requestUser);
            resultDAO.setCommonInfo(commonInfo);

            resultRepository.save(resultDAO);

            break;
        }

        return resultDTO;
    }

    private long calculate(long count, long remainPrice) {
        return remainPrice/count;
    }
}
