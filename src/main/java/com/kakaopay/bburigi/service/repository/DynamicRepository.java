package com.kakaopay.bburigi.service.repository;

import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import com.kakaopay.bburigi.entity.DAO.BburigiSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DynamicRepository implements IDynamicRepository {

    private final ResultRepository resultRepository;
    private final SourceRepository sourceRepository;

    @Autowired
    public DynamicRepository(ResultRepository resultRepository, SourceRepository sourceRepository) {
        this.resultRepository = resultRepository;
        this.sourceRepository = sourceRepository;
    }

    @Override
    public BburigiSource findAvailableSource(String token, long owner, Date date) {
        return sourceRepository.searchMyBburigi(token, owner, date);
    }

    @Override
    public List<BburigiResult> findAvailableResult(String token, Long requestUser, String room, Date date) {
        BburigiResult checkResult = resultRepository.searchRequestedResult(requestUser, date);

        if (checkResult != null) {
            return new ArrayList<>();
        }

        return resultRepository.searchNotPaidResult(token, requestUser, room, date);
    }
}
