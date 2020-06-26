package com.kakaopay.bburigi.entity.DAO;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity
@EqualsAndHashCode
public class BburigiResult {

    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private Long sourceUser;

    @Embedded
    private BburigiCommonInfo commonInfo;

    public BburigiResult() {}

    public BburigiResult(String token, Long sourceUser, BburigiCommonInfo commonInfo) {
        this.token = token;
        this.sourceUser = sourceUser;
        this.commonInfo = commonInfo;
    }

    public void changeOwner (Long ownerUser) {
        this.commonInfo.changeOwner(ownerUser);
    }
}
