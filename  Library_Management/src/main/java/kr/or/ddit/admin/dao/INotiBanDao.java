package kr.or.ddit.admin.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.Noti;

public interface INotiBanDao {
	 //1) 반납예정 하루 전 건수 
	int countDueNotifications();
	
	//2) 연체 하루 후 건수
	int countOverdueNotifications();
	
	//3) 반납예정 하루 전 상세 목록
	List<Map<String,Object>> listDueNotifications();
	
	//4) 연체 하루 후 상세 목록
	List<Map<String,Object>> listOverdueNotifications();
	
	//알림 이력 남기기
	int insertWarningHistory(Noti noti);
}
