package com.kakaopay.bburigi.service;

import com.kakaopay.bburigi.entity.DAO.BburigiResult;
import com.kakaopay.bburigi.entity.DTO.ResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
class BburigiServiceTest {

    long count = 5;
    long price = 135000L;
    String token = "QCY";
    Long ownerUser = 202006261435L;
    String roomId = "room_id_ljk";

    @Autowired
    private IBburigiService bburigiService;

    @Autowired
    private ISourceService sourceService;

    @Autowired
    private IResultService resultService;

    @Test
    void createBburigi() {
        try {
            System.out.println(bburigiService.createBburigi(ownerUser, roomId, price, count));
        } catch (Exception ex) {
            System.out.println("Exception occur : " + ex.getMessage());
        }
    }

    @Test
    void createSource() {
        Calendar calendar = Calendar.getInstance();

        try {
            // 비어 있는 요청 값, expire 가 오늘
//            sourceService.createSource(token, ownerUser, roomId, price, count, new Date(), null);

            calendar.add(Calendar.MINUTE, 10);
            List<BburigiResult> resultList = resultService.createResult(token, ownerUser, roomId, price, count, calendar.getTime());

            calendar.add(Calendar.MINUTE, -10);
            calendar.add(Calendar.DAY_OF_MONTH, 7);

            // 정상
            sourceService.createSource(token, ownerUser, roomId, price, count, calendar.getTime(), resultList);
//
//            // 중복 token
//            sourceService.createSource("QCY", 202006261435L, "room_id_ljk_2", 13598713841L, 435, calendar.getTime(), resultList);
//
//            // 비어 있는 요청 값
//            sourceService.createSource("A7y", 202006261435L, "room_id_ljk_2", 13598713841L, 435, calendar.getTime(), null);
//
//            // token 자리수
//            sourceService.createSource("토큰자리수", 202006261435L, "room_id_ljk_2", 13598713841L, 435, calendar.getTime(), resultList);

            System.out.println("J Tag");
        } catch (Exception ex) {
            System.out.println("J Tag, failure");
        }
    }

    @Test
    void getUniqueTokenTimeOut() {
        // 정상건은 searchUniqueToken method의 thread sleep 제거 필요
        try {
            String token = sourceService.getUniqueToken();

            System.out.println("J Tag");
        } catch (Exception ex) {
            System.out.println("J Tag, failure");
        }
    }

    @Test
    void getSource() {
        try {
            sourceService.getSource(token, ownerUser);
            Assert.notNull(sourceService.getSource("없는것", 202006261435L), "null");
            System.out.println("J Tag");
        } catch (Exception ex) {
            System.out.println("J Tag, failure");
        }
    }

    @Test
    void getAvailableResult() {
        try {
            Long randomRequestedId = new Random().nextLong();
            Assert.isNull(resultService.getAvailableResult(token, ownerUser, roomId, new Date()), "owner 로 찾으면 안됨");

            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.YEAR, 1);
            Assert.isNull(resultService.getAvailableResult(token, randomRequestedId, roomId, calendar.getTime()), "기간 만료");

            calendar.add(Calendar.YEAR, -2);

            ResultDTO dto = resultService.getAvailableResult(token, randomRequestedId, roomId, calendar.getTime());

            Assert.notNull(dto, "찾을 수 있어야 함");
            System.out.println("J Tag");
        } catch (Exception ex) {
            System.out.println("J Tag, failure");
        }
    }
}