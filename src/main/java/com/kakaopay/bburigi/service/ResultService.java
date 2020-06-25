package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.BburigiResult;
import com.kakaopay.bburigi.service.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService implements IResultService {

    @Override
    public List<BburigiResult> createResult(long ownerUser, String room, long price, long count) {
        List<BburigiResult> resultList = new ArrayList<>();

        long remainPrice = price;

        for (long i = 0L; i < count; i++) {
            long resultPrice = this.calculate(count, remainPrice);

            remainPrice -= resultPrice;

            if (i+1 == count) {
                resultPrice += remainPrice;
            }

            BburigiResult result = new BburigiResult();
            result.setPrice(resultPrice);

            resultList.add(result);
        }

        return resultList;
    }

    @Override
    public boolean checkResult(String token, long requestUser, String room) {

    }

    private long calculate(long count, long remainPrice) {
        return remainPrice/count;
    }
}
