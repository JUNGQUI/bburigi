package com.kakaopay.bburigi.entity.DAO;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode
public class BburigiSource {

    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 3, unique = true)
    private String token;
    private long count;

    @Embedded
    private BburigiCommonInfo commonInfo;

    @OneToMany
    private List<BburigiResult> result;

    public BburigiSource(){}
    public BburigiSource(String token, long count, BburigiCommonInfo bburigiCommonInfo, List<BburigiResult> result) {
        this.token = token;
        this.count = count;
        this.commonInfo = bburigiCommonInfo;
        this.result = result;
    }
}
