package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.DAO.BburigiCommonInfo;
import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import com.kakaopay.bburigi.entity.DAO.BburigiSource;
import com.kakaopay.bburigi.entity.DTO.ResultDTO;
import com.kakaopay.bburigi.entity.DTO.SourceDTO;
import com.kakaopay.bburigi.service.repository.SourceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SourceService implements ISourceService {

    private final SourceRepository sourceRepository;

    @Autowired
    public SourceService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @Override
    public void createSource(String token, long owner, String room, long price, long count, Date expire,
                               List<BburigiResult> resultList) {
        BburigiCommonInfo commonInfo = new BburigiCommonInfo(owner, room, price, expire);
        BburigiSource source = new BburigiSource(token, count, commonInfo, resultList);

        sourceRepository.save(source);
    }

    @Override
    public String getUniqueToken() {
        String token;

        do {
            token = RandomStringUtils.random(3, true, true);
        } while (sourceRepository.findByToken(token) != null);

        return token;
    }

    @Override
    public SourceDTO getSource(String token, long owner) {
        BburigiSource bburigiSource = sourceRepository
                .findByTokenAndCommonInfo_OwnerUserAndCommonInfo_ExpireAfter(token, owner, new Date());

        if (bburigiSource == null) {
            return null;
        }

        List<ResultDTO> resultDTOList = new ArrayList<>();

        long paidPrice = 0L;

        for (BburigiResult resultDAO : bburigiSource.getResult()) {
            ResultDTO resultDTO = new ResultDTO(
                    resultDAO.getCommonInfo().getPrice(),
                    resultDAO.getCommonInfo().getOwnerUser()
            );

            paidPrice += resultDAO.getCommonInfo().getPrice();
            resultDTOList.add(resultDTO);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bburigiSource.getCommonInfo().getExpire());
        calendar.add(Calendar.DAY_OF_MONTH, -7);

        SourceDTO sourceDTO = new SourceDTO(
                calendar.getTime(), bburigiSource.getCommonInfo().getPrice(), paidPrice, resultDTOList
        );

        return sourceDTO;
    }
}
