package com.kakaopay.bburigi.entity.DAO;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Embeddable
public class BburigiCommonInfo {

    private long price;
    private Long ownerUser;
    private String room;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;

    public BburigiCommonInfo() {}

    public BburigiCommonInfo(Long ownerUser, String room, long price, Date expire) {
        this.ownerUser = ownerUser;
        this.room = room;
        this.price = price;
        this.expire = expire;
    }
}
