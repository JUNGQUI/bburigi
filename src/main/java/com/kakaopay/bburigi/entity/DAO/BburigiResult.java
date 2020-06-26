package com.kakaopay.bburigi.entity.DAO;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Data
@EqualsAndHashCode
@Entity
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
}
