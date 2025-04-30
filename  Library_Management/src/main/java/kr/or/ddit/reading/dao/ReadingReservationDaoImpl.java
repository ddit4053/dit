package kr.or.ddit.reading.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil;
import kr.or.ddit.vo.ReadingReservationsVo;

public class ReadingReservationDaoImpl implements IReadingReservationDao {

    @Override
    public int insertReservation(ReadingReservationsVo vo) {
        int cnt = 0;

        // room_name 없이 INSERT
        String sql = "INSERT INTO reading_reservations "
                   + "(r_reserve_no, reserve_date, start_time, end_time, r_reserve_status, user_no, seat_no) "
                   + "VALUES (reading_reservations_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(vo.getReserveDate()));
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(vo.getReserveDate().atTime(vo.getStartTime())));
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(vo.getReserveDate().atTime(vo.getEndTime())));
            ps.setString(4, vo.getrReserveStatus());
            ps.setInt(5, vo.getUserNo());
            ps.setInt(6, vo.getSeatNo());

            cnt = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cnt;
    }

    @Override
    public List<ReadingReservationsVo> selectReservationsByUser(int userNo) {
        List<ReadingReservationsVo> list = new ArrayList<>();

        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "WHERE r.user_no = ? AND r.r_reserve_status = '예약완료' " +
                     "ORDER BY r.reserve_date, r.start_time";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReadingReservationsVo vo = new ReadingReservationsVo();
                vo.setrReserveNo(rs.getInt("r_reserve_no"));
                vo.setUserNo(rs.getInt("user_no"));
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setReserveDate(rs.getDate("reserve_date").toLocalDate());
                vo.setStartTime(rs.getTime("start_time").toLocalTime());
                vo.setEndTime(rs.getTime("end_time").toLocalTime());
                vo.setrReserveStatus(rs.getString("r_reserve_status"));
                vo.setRoomName(rs.getString("room_name"));
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<ReadingReservationsVo> selectReservationsBySeat(int seatNo) {
        List<ReadingReservationsVo> list = new ArrayList<>();

        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "WHERE r.seat_no = ? AND r.r_reserve_status = '예약완료' " +
                     "ORDER BY r.reserve_date, r.start_time";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seatNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReadingReservationsVo vo = new ReadingReservationsVo();
                vo.setrReserveNo(rs.getInt("r_reserve_no"));
                vo.setUserNo(rs.getInt("user_no"));
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setReserveDate(rs.getDate("reserve_date").toLocalDate());
                vo.setStartTime(rs.getTime("start_time").toLocalTime());
                vo.setEndTime(rs.getTime("end_time").toLocalTime());
                vo.setrReserveStatus(rs.getString("r_reserve_status"));
                vo.setRoomName(rs.getString("room_name"));
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<ReadingReservationsVo> selectAllReservations() {
        List<ReadingReservationsVo> list = new ArrayList<>();

        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "ORDER BY r.reserve_date, r.start_time";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReadingReservationsVo vo = new ReadingReservationsVo();
                vo.setrReserveNo(rs.getInt("r_reserve_no"));
                vo.setUserNo(rs.getInt("user_no"));
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setReserveDate(rs.getDate("reserve_date").toLocalDate());
                vo.setStartTime(rs.getTime("start_time").toLocalTime());
                vo.setEndTime(rs.getTime("end_time").toLocalTime());
                vo.setrReserveStatus(rs.getString("r_reserve_status"));
                vo.setRoomName(rs.getString("room_name"));
                list.add(vo);
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

    @Override
    public List<ReadingReservationsVo> selectReservationsByRoomAndDate(String roomName, LocalDate reserveDate) {
        List<ReadingReservationsVo> list = new ArrayList<>();

        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "WHERE rm.room_name = ? AND r.reserve_date = ? AND r.r_reserve_status = '예약완료' " +
                     "ORDER BY r.start_time";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomName);
            ps.setDate(2, Date.valueOf(reserveDate));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReadingReservationsVo vo = new ReadingReservationsVo();
                vo.setrReserveNo(rs.getInt("r_reserve_no"));
                vo.setUserNo(rs.getInt("user_no"));
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setReserveDate(rs.getDate("reserve_date").toLocalDate());
                vo.setStartTime(rs.getTime("start_time").toLocalTime());
                vo.setEndTime(rs.getTime("end_time").toLocalTime());
                vo.setrReserveStatus(rs.getString("r_reserve_status"));
                vo.setRoomName(rs.getString("room_name"));
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<ReadingReservationsVo> selectReservationsBySeatAndDate(int seatNo, LocalDate reserveDate) {
        List<ReadingReservationsVo> list = new ArrayList<>();

        String sql = "SELECT r.*, rm.room_name " +
                     "FROM reading_reservations r " +
                     "JOIN reading_seats s ON r.seat_no = s.seat_no " +
                     "JOIN reading_rooms rm ON s.room_no = rm.room_no " +
                     "WHERE r.seat_no = ? AND r.reserve_date = ? AND r.r_reserve_status = '예약완료' " +
                     "ORDER BY r.start_time";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seatNo);
            ps.setDate(2, Date.valueOf(reserveDate));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReadingReservationsVo vo = new ReadingReservationsVo();
                vo.setrReserveNo(rs.getInt("r_reserve_no"));
                vo.setUserNo(rs.getInt("user_no"));
                vo.setSeatNo(rs.getInt("seat_no"));
                vo.setReserveDate(rs.getDate("reserve_date").toLocalDate());
                vo.setStartTime(rs.getTime("start_time").toLocalTime());
                vo.setEndTime(rs.getTime("end_time").toLocalTime());
                vo.setrReserveStatus(rs.getString("r_reserve_status"));
                vo.setRoomName(rs.getString("room_name"));
                list.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
