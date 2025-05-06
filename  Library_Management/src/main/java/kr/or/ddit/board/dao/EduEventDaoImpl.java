package kr.or.ddit.board.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.util.MybatisDao;

public class EduEventDaoImpl extends MybatisDao implements IEduEventDao {
    
    private static EduEventDaoImpl instance;

    private EduEventDaoImpl() {
        // 생성자는 private으로 선언
    }

    public static EduEventDaoImpl getInstance() {
        if (instance == null) {
            instance = new EduEventDaoImpl();
        }
        return instance;
    }
    
    @Override
    public int countEduEvents(Map<String, Object> params) {
        return selectOne("eduEvent.countEduEvents", params);
    }
    
    @Override
    public List<Map<String, Object>> selectEduEvents(Map<String, Object> params) {
        return selectList("eduEvent.selectEduEvents", params);
    }
    
    @Override
    public Map<String, Object> selectEduEventDetail(int evNo) {
        return selectOne("eduEvent.selectEduEventDetail", evNo);
    }
    
    @Override
    public List<Map<String, Object>> selectEventImages(int evNo) {
        return selectList("eduEvent.selectEventImages", evNo);
    }
    
    @Override
    public int insertEventRequest(Map<String, Object> params) {
        return insert("eduEvent.insertEventRequest", params);
    }
    
    @Override
    public int checkEventRequest(Map<String, Object> params) {
        return selectOne("eduEvent.checkEventRequest", params);
    }
    
    @Override
    public int deleteEventRequest(Map<String, Object> params) {
        return delete("eduEvent.deleteEventRequest", params);
    }
    
    @Override
    public List<Map<String, Object>> selectRelatedEvents(Map<String, Object> params) {
        return selectList("eduEvent.selectRelatedEvents", params);
    }

	@Override
	public List<Map<String, Object>> selectEventVideos(int evNo) {
		
		return selectList("eduEvent.selectEventVideos", evNo);
	}
}