package com.kakaopay.bburigi.entity.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@EqualsAndHashCode
public class SourceDTO {
    private Date createdDate;
    private long price;
    private long paidPrice;

    private List<ResultDTO> resultList;

    public SourceDTO(Date createdDate, long price, long paidPrice, List<ResultDTO> resultList) {
        this.createdDate = createdDate;
        this.price = price;
        this.paidPrice = paidPrice;
        this.resultList = resultList;
    }
}
