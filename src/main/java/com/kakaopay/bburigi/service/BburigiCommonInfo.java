package com.kakaopay.bburigi.service;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Embeddable
public class BburigiCommonInfo {

    private long price;
    private long ownerUser;
    private String room;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;
}
