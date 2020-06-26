package com.kakaopay.bburigi.entity.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class RequestSource {
    private long price;
    private long count;
}
