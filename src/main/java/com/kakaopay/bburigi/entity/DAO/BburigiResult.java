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
    private long sourceUser;

    @Embedded
    private BburigiCommonInfo commonInfo;
}