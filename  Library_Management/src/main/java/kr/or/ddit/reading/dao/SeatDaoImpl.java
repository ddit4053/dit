package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.vo.ReadingSeatsVo;
import kr.or.ddit.vo.ReadingSeatsVo;

public class SeatDaoImpl implements SeatDao {

    @Override
    public List<ReadingSeatsVo> selectSeatsByRoom(String roomName) {
        List<ReadingSeatsVo> list = new ArrayList<>();
        
        String sql = "SELECT * FROM reading_seats WHERE room_no = (SELECT room_no FROM reading_rooms WHERE room_name = ?)";



        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	ReadingSeatsVo vo = new ReadingSeatsVo();
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setSeatNumber(rs.getString("seat_number"));
                vo.setIsAvailable(rs.getString("is_available"));
                vo.setRoomNo(rs.getInt("room_no"));
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
