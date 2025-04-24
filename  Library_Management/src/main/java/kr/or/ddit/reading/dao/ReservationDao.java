package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import kr.or.ddit.vo.ReadingReservationsVo;

public class ReservationDao {
    private final Connection conn;

    public ReservationDao(Connection conn) {
        this.conn = conn;
    }

    public int insertReservation(ReadingReservationsVo vo) throws SQLException {
        String sql = "INSERT INTO reading_reservations (r_reserve_no, start_time, end_time, r_reserve_status, user_no, seat_no) " +
                     "VALUES (reading_reserve_seq.NEXTVAL, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vo.getStartTime());
            ps.setString(2, vo.getEndTime());
            ps.setString(3, vo.getRReserveStatus());
            ps.setInt(4, vo.getUserNo());
            ps.setInt(5, vo.getSeatNo());
            return ps.executeUpdate();
        }
    }
}
