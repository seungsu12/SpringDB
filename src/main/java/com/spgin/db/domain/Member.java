package com.spgin.db.domain;

import lombok.Data;

@Data
public class Member {
    public String memberId;
    public int money;

    public Member() {
    }

    public Member(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }
}
