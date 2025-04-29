package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.vo.ReadingReservationsVo;

public class MyReservationDaoImpl implements MyReservationDao {

    @Override
    public List<ReadingReservationsVo> getReservationsByUser(int userNo) {
        List<ReadingReservationsVo> list = new ArrayList<>();
        String sql = "SELECT * FROM reading_reservations WHERE user_no = ? ORDER BY start_time";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReadingReservationsVo vo = new ReadingReservationsVo();
                vo.setRReserveNo(rs.getInt("r_reserve_no"));
                vo.setUserNo(rs.getInt("user_no"));
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setReserveDate(rs.getDate("reserve_date").toLocalDate());   // Date -> LocalDate
                vo.setStartTime(rs.getTime("start_time").toLocalTime());        // Time -> LocalTime
                vo.setEndTime(rs.getTime("end_time").toLocalTime());            // Time -> LocalTime
                vo.setRReserveStatus(rs.getString("r_reserve_status"));
                vo.setRoomName(rs.getString("room_name"));                     // roomName 추가
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
