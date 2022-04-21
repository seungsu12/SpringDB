package com.spgin.db.repository;

import com.spgin.db.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {
    
    MemberRepositoryV0 repository = new MemberRepositoryV0();
    MemberRepositoryV1 repositoryV1;
    
    @Test
    void save() throws SQLException {
        Member memberV0 = new Member("memberV0", 10000);
        repository.save(memberV0);
    }
}