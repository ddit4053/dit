package kr.or.ddit.board.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.board.dao.EduEventDaoImpl;
import kr.or.ddit.board.dao.IEduEventDao;

public class EduEventServiceImpl implements IEduEventService {
    
    private static EduEventServiceImpl instance;
    private IEduEventDao eduEventDao;

    private EduEventServiceImpl() {
    	eduEventDao = EduEventDaoImpl.getInstance();
    }

    public static EduEventServiceImpl getInstance() {
        if (instance == null) {
            instance = new EduEventServiceImpl();
        }
        return instance;
    }
    
    @Override
    public int countEduEvents(Map<String, Object> params) {
        return eduEventDao.countEduEvents(params);
    }
    
    @Override
    public List<Map<String, Object>> selectEduEvents(Map<String, Object> params) {
        return eduEventDao.selectEduEvents(params);
    }
    
    @Override
    public Map<String, Object> selectEduEventDetail(int evNo) {
        return eduEventDao.selectEduEventDetail(evNo);
    }
    
    @Override
    public List<Map<String, Object>> selectEventImages(int evNo) {
        return eduEventDao.selectEventImages(evNo);
    }
    
    @Override
    public int insertEventRequest(Map<String, Object> params) {
        return eduEventDao.insertEventRequest(params);
    }
    
    @Override
    public int checkEventRequest(Map<String, Object> params) {
        return eduEventDao.checkEventRequest(params);
    }
    
    @Override
    public int deleteEventRequest(Map<String, Object> params) {
        return eduEventDao.deleteEventRequest(params);
    }
    
    @Override
    public List<Map<String, Object>> selectRelatedEvents(Map<String, Object> params) {
        return eduEventDao.selectRelatedEvents(params);
    }

	@Override
	public List<Map<String, Object>> selectEventVideos(int evNo) {
		
		return eduEventDao.selectEventVideos(evNo);
	}
}