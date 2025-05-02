package kr.or.ddit.admin.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import kr.or.ddit.admin.controller.EmailSender;
import kr.or.ddit.admin.dao.INotiBanDao;
import kr.or.ddit.admin.dao.NotibanDaoImpl;
import kr.or.ddit.vo.Noti;


public class NotiBanServiceImpl implements INotiBanService {
	
	//dao 객체 필요
	private INotiBanDao dao;
	
	private NotiBanServiceImpl() {
		dao = NotibanDaoImpl.getDao();
	}
	
	//자신의 객체 생성 리턴
	private static INotiBanService service;
	public static INotiBanService getService() {
		if(service == null) service = new NotiBanServiceImpl();
		
		return service;
	}
	
	@Override
	public int countDueNotifications() {
		// TODO Auto-generated method stub
		return dao.countDueNotifications();
	}

	@Override
	public int countOverdueNotifications() {
		// TODO Auto-generated method stub
		return dao.countOverdueNotifications();
	}

	@Override
	public List<Map<String, Object>> listDueNotifications() {
		// TODO Auto-generated method stub
		return dao.listDueNotifications();
	}

	@Override
	public List<Map<String, Object>> listOverdueNotifications() {
		// TODO Auto-generated method stub
		return dao.listOverdueNotifications();
	}

	@Override
	public boolean sendDueReminders() {
	    boolean ok = true;
	    List<Map<String,Object>> list = dao.listDueNotifications();

	    for (Map<String,Object> item : list) {
	        // ── ① 수신자, 기본정보 추출 ──
	        String to = (String) item.get("email");
	        int    userNo = ((BigDecimal)item.get("userNo")).intValue();
	        String userName  = (String) item.get("userName");
	        String bookTitle = (String) item.get("bookTitle");
	        String dueDate   = (String) item.get("dueDate");  // YYYY-MM-DD 포맷
	        
	        // ── ② 메일 제목 / 본문 구성 ──
	        String subject = "[도서관] 내일 반납 예정 알림";
	        String body = String.format(
	            "%s님, 도서 \"%s\"의 반납 예정일이 내일(%s) 입니다. 내일이 지나면  도서대출연장이 제한됩니다.",
	            userName, bookTitle, dueDate
	        );
	        
	        // ── ③ 실제 발송 ──
	        boolean sent = EmailSender.getInstance().send(to, subject, body);
	        
	        // ── ④ 성공 시에만 히스토리 남기기 ──
	        if (sent) {
	            Noti noti = new Noti();
	            noti.setUserNo(userNo);
	            noti.setMessage(body);
	            // 매퍼에 하드코딩된 NOTI_TYPE('OVERDUE_WARNING') 그대로 쓰려면 안 세팅해도 무방
	            int inserted = dao.insertWarningHistory(noti);
	            ok &= (inserted > 0);
	        } else {
	            ok = false;
	        }
	    }
	    return ok;
	}


	@Override
	public boolean sendOverdueAlerts() {
	    boolean ok = true;
	    List<Map<String,Object>> list = dao.listOverdueNotifications();

	    for (Map<String,Object> item : list) {
	        // ── ① 수신자, 기본정보 추출 ──
	        String to = (String) item.get("email");
	        int    userNo    = ((BigDecimal)item.get("userNo")).intValue();
	        String userName  = (String) item.get("userName");
	        String bookTitle = (String) item.get("bookTitle");
	        String warnDate  = (String) item.get("overdueDays"); // YYYY-MM-DD 포맷
	        
	        // ── ② 메일 제목 / 본문 구성 ──
	        String subject = "[도서관] 연체 예정 알림";
	        String body = String.format(
	            "%s님, 도서 \"%s\"의 연체 예정일이 내일(%s)까지입니다. 내일이 지나면 연체자로 지정되어 도서대출이 제한됩니다.",
	            userName, bookTitle, warnDate
	        );
	        
	        // ── ③ 실제 발송 ──
	        boolean sent = EmailSender.getInstance().send(to, subject, body);
	        
	        // ── ④ 성공 시에만 히스토리 남기기 ──
	        if (sent) {
	            Noti noti = new Noti();
	            noti.setUserNo(userNo);
	            noti.setMessage(body);
	            int inserted = dao.insertWarningHistory(noti);
	            ok &= (inserted > 0);
	        } else {
	            ok = false;
	        }
	    }
	    return ok;
	}

}
