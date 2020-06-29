package com.kakaopay.bburigi.controller;

import com.kakaopay.bburigi.entity.DTO.RequestToken;
import com.kakaopay.bburigi.entity.DTO.ResultDTO;
import com.kakaopay.bburigi.entity.DTO.SourceDTO;
import com.kakaopay.bburigi.entity.DTO.RequestSource;
import com.kakaopay.bburigi.entity.common.JsonResult;
import com.kakaopay.bburigi.service.IBburigiService;
import com.kakaopay.bburigi.service.IResultService;
import com.kakaopay.bburigi.service.ISourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class BurigiController {

    private static final Logger logger = LoggerFactory.getLogger(BurigiController.class);

    private final IBburigiService bburigiService;
    private final ISourceService sourceService;
    private final IResultService resultService;

    @Autowired
    public BurigiController(IBburigiService bburigiService, ISourceService sourceService, IResultService resultService) {
        this.bburigiService = bburigiService;
        this.sourceService = sourceService;
        this.resultService = resultService;
    }

    @PostMapping(value = "/createBburigi")
    public JsonResult createBburigi(
            @RequestHeader("X-USER-ID") Long userId, @RequestHeader("X-ROOM-ID") String roomId,
//            @RequestParam(value = "price") long price, @RequestParam(value = "count") long count) {
            @RequestBody RequestSource requestSource) {
        try {
            Assert.isTrue(requestSource.getPrice() != 0L && requestSource.getCount() != 0L, "뿌릴 금액과 나눠줄 사람 수는 0일 수 없습니다.");
            String token = bburigiService.createBburigi(userId, roomId, requestSource.getPrice(), requestSource.getCount());

            if (!StringUtils.hasText(token)) {
                logger.error("token 발급 중 error 발생, timeout");
                return JsonResult.failure("뿌리기 실패 했습니다. 잠시 후 다시 시도해 주세요.");
            }

            return JsonResult.success(token);
        } catch (Exception ex) {
            logger.error("뿌리기 생성 중 error 발생, " + ex.getMessage());

            if (requestSource.getCount() == 0 && requestSource.getPrice() == 0) {
                return JsonResult.failure(ex.getMessage());
            }

            return JsonResult.failure("뿌리기 실패 했습니다. 잠시 후 다시 시도해 주세요.");
        }
    }

    @PostMapping(value = "/requestResult")
    public JsonResult requestResult(
            @RequestHeader("X-USER-ID") Long userId, @RequestHeader("X-ROOM-ID") String roomId,
//            @RequestParam(value = "token") String token) {
            @RequestBody RequestToken token) {
        try {
            ResultDTO resultDTO = resultService.getAvailableResult(token.getToken(), userId, roomId, new Date());

            if (resultDTO == null) {
                return JsonResult.failure("뿌리기 받기 실패했습니다. 받을 수 있는 건이 없습니다.");
            }

            return JsonResult.success(resultDTO.getPrice());
        } catch (Exception ex) {
            logger.error("뿌리기 받기 요청 중 error 발생, " + ex.getMessage());
            return JsonResult.failure("뿌리기 받기 실패했습니다. 잠시 후 다시 시도해 주세요.");
        }
    }

    @PostMapping(value = "/requestSource")
    public JsonResult requestSource(
            @RequestHeader("X-USER-ID") Long userId, @RequestHeader("X-ROOM-ID") String roomId,
//            @RequestParam(value = "token") String token) {
            @RequestBody RequestToken token) {
        try {
            SourceDTO sourceDTO = sourceService.getSource(token.getToken(), userId);

            if (sourceDTO == null) {
                return JsonResult.failure("뿌리기 조회 실패했습니다. 조회 할 수 있는 뿌리기가 없습니다.");
            }

            return JsonResult.success(sourceDTO);
        }catch (Exception ex) {
            logger.error("뿌리기 조회 요청 중 error 발생, " + ex.getMessage());
            return JsonResult.failure("뿌리기 조회 실패했습니다. 잠시 후 다시 시도해 주세요.");
        }
    }
}
