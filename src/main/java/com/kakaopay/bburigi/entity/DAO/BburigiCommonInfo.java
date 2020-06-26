package com.kakaopay.bburigi.entity.DAO;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Embeddable
public class BburigiCommonInfo {

    private long price;
    private Long ownerUser;
    private String room;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;

    public BburigiCommonInfo() {}

    public BburigiCommonInfo(Long ownerUser, String room, long price, Date expire) {
        this.price = price;
        this.ownerUser = ownerUser;
        this.room = room;
        this.expire = expire;
    }

    public void changeOwner(Long ownerUser) {
        this.ownerUser = ownerUser;
    }
}
