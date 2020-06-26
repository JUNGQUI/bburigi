package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.DAO.BburigiCommonInfo;
import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import com.kakaopay.bburigi.entity.DTO.ResultDTO;
import com.kakaopay.bburigi.service.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResultService implements IResultService {

    private final ResultRepository resultRepository;

    @Autowired
    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public List<BburigiResult> createResult(String token, long sourceUser, String room, long price, long count, Date date) {
        List<BburigiResult> resultList = new ArrayList<>();

        long remainPrice = price;

        for (long i = 0L; i < count; i++) {
            long resultPrice = this.calculate(count, remainPrice);

            remainPrice -= resultPrice;

            if (i+1 == count) {
                resultPrice += remainPrice;
            }

            BburigiCommonInfo commonInfo = new BburigiCommonInfo(-1L, room, resultPrice, date);

            BburigiResult result = new BburigiResult();
            result.setSourceUser(sourceUser);
            result.setToken(token);
            result.setCommonInfo(commonInfo);

            resultRepository.save(result);

            resultList.add(result);
        }

        return resultList;
    }

    @Override
    public List<ResultDTO> getAvailableResult(String token, long requestUser, String room, Date date) {
        return null;
    }

    private long calculate(long count, long remainPrice) {
        return remainPrice/count;
    }
}
