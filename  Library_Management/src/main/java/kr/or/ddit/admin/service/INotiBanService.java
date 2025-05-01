package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

public interface INotiBanService {
	//1) 반납예정 하루 전 건수 
	int countDueNotifications();
		
	//2) 연체 하루 후 건수
	int countOverdueNotifications();
		
	//3) 반납예정 하루 전 상세 목록
	List<Map<String,Object>> listDueNotifications();
		
	//4) 연체 하루 후 상세 목록
	List<Map<String,Object>> listOverdueNotifications();
		
	//5) 반납예정 하루 전 대상자들에게 알림
	boolean sendDueReminders();
	
	//6) 연체 하루 전 대상자들에게 알림
	boolean sendOverdueAlerts();
}
