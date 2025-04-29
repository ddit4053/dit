package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.vo.ReadingReservationsVo;

public class ReadingReservationDaoImpl implements ReadingReservationDao {

    @Override
    public int insertReservation(ReadingReservationsVo vo) {
        int cnt = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO READING_RESERVATIONS (R_RESERVE_NO, USER_NO, SEAT_NO, RESERVE_DATE, START_TIME, END_TIME, R_RESERVE_STATUS) " +
                         "VALUES (SEQ_READING_RESERVATIONS.NEXTVAL, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, vo.getUserNo());
            pstmt.setInt(2, vo.getSeatNo());
            pstmt.setDate(3, Date.valueOf(vo.getReserveDate()));
            pstmt.setTime(4, Time.valueOf(vo.getStartTime()));
            pstmt.setTime(5, Time.valueOf(vo.getEndTime()));
            pstmt.setString(6, vo.getRReserveStatus());

            cnt = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
        return cnt;
    }

    @Override
    public List<ReadingReservationsVo> selectReservationsByUser(int userNo) {
        List<ReadingReservationsVo> list = new ArrayList<>();
        String sql = "SELECT * FROM READING_RESERVATIONS WHERE USER_NO = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(makeVoFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ReadingReservationsVo> selectReservationsBySeat(int seatNo) {
        List<ReadingReservationsVo> list = new ArrayList<>();
        String sql = "SELECT * FROM READING_RESERVATIONS WHERE SEAT_NO = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seatNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(makeVoFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ReadingReservationsVo> selectByUserNo(int userNo) {
        return selectReservationsByUser(userNo);
    }

    private ReadingReservationsVo makeVoFromResultSet(ResultSet rs) throws Exception {
        ReadingReservationsVo vo = new ReadingReservationsVo();
        vo.setRReserveNo(rs.getInt("r_reserve_no"));
        vo.setUserNo(rs.getInt("user_no"));
        vo.setSeatNo(rs.getInt("seat_no"));
        vo.setReserveDate(rs.getDate("reserve_date").toLocalDate());
        vo.setStartTime(rs.getTime("start_time").toLocalTime());
        vo.setEndTime(rs.getTime("end_time").toLocalTime());
        vo.setRReserveStatus(rs.getString("r_reserve_status"));
        return vo;
    }
}
