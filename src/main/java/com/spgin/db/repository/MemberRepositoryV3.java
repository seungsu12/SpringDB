package com.spgin.db.repository;

import com.spgin.db.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

// jdbc - driverManager 사용
@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV3 {

    private final DataSource dataSource;

    public Member save(Member member) throws SQLException{
        String sql = "insert into member(member_id,money) values(?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {

            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,member.getMemberId());
            pstmt.setInt(2,member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error",e);
            throw e;
        }finally {
            close(conn,pstmt,null);
        }


    }

    public Member findById(String id) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs =null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }else {
                throw new NoSuchElementException("member not found memberId ="+id);
            }

        } catch (SQLException e) {
            log.error("error {}",e);
            throw e;
        }finally {
            close(conn,pstmt,rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id =?";
        Connection conn = null;
        PreparedStatement pstmt =null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(2, memberId);
            pstmt.setInt(1, money);
            int resultSize = pstmt.executeUpdate();
            log.info("result size = {}",resultSize);
        } catch (SQLException e) {
            log.error("error {}",e);
            throw e;
        }finally {
            close(conn,pstmt,rs);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql ="delete from member where memberId =?";
        Connection conn=null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }finally {
            close(conn,pstmt,null);
        }
    }

    private void close(Connection conn , Statement stmt , ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);

        DataSourceUtils.releaseConnection(conn,dataSource);


    }

    private Connection getConnection() throws SQLException {

        Connection conn = DataSourceUtils.getConnection(dataSource);
        log.info("getConnection ={} ,class={}");
        return conn;

    }
}
