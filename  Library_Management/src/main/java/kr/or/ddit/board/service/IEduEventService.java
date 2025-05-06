package kr.or.ddit.board.service;

import java.util.List;
import java.util.Map;

public interface IEduEventService {
    
    public int countEduEvents(Map<String, Object> params);
    
    public List<Map<String, Object>> selectEduEvents(Map<String, Object> params);
    
    public Map<String, Object> selectEduEventDetail(int evNo);
    
    public List<Map<String, Object>> selectEventImages(int evNo);
    
    public int insertEventRequest(Map<String, Object> params);
    
    public int checkEventRequest(Map<String, Object> params);
    
    public int deleteEventRequest(Map<String, Object> params);
    
    public List<Map<String, Object>> selectRelatedEvents(Map<String, Object> params);
    
    public List<Map<String, Object>> selectEventVideos(int evNo);
}