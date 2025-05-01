package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                vo.setStartTime(rs.getString("start_time"));
                vo.setEndTime(rs.getString("end_time"));
                vo.setRReserveStatus(rs.getString("r_reserve_status"));
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setUserNo(rs.getInt("user_no"));
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
