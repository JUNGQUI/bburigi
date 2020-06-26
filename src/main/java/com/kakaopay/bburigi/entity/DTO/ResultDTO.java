package com.kakaopay.bburigi.entity.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ResultDTO {
    private long paidPrice;
    private long owner;

    public ResultDTO(long paidPrice, long owner) {
        this.paidPrice = paidPrice;
        this.owner = owner;
    }
}
