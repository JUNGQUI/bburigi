package com.kakaopay.bburigi.entity.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
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
