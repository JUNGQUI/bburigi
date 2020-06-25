package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.BburigiResult;
import com.kakaopay.bburigi.entity.BburigiSource;
import com.kakaopay.bburigi.service.repository.SourceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceService implements ISourceService {

    private final SourceRepository sourceRepository;

    @Autowired
    public SourceService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @Override
    public String createSource(long owner, String room, long price, long count, List<BburigiResult> resultList) {

        String token = this.getUniqueToken();

        BburigiSource source = new BburigiSource(token, count, price, owner, room);

        sourceRepository.saveAndFlush(source);

        return token;
    }

    @Override
    public String getUniqueToken() {
        String token;

        do {
            token = RandomStringUtils.random(3, true, true);
        } while (sourceRepository.findByToken(token) != null);

        return token;
    }
}
