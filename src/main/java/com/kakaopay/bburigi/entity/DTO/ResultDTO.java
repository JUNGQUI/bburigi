package com.kakaopay.bburigi.entity.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ResultDTO {
    private long price;
    private Long owner;

    public ResultDTO(long price, Long owner) {
        this.price = price;
        this.owner = owner;
    }
}
