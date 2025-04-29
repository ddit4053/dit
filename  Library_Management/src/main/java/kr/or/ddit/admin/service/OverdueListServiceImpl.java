package kr.or.ddit.admin.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import kr.or.ddit.admin.controller.EmailSender;
import kr.or.ddit.admin.dao.IOverdueListDao;
import kr.or.ddit.admin.dao.OverdueListDaoImpl;
import kr.or.ddit.vo.Noti;

public class OverdueListServiceImpl implements IOverdueListService {
	
	//dao 객체 필요
	private IOverdueListDao dao;
	
	private OverdueListServiceImpl() {
		dao = OverdueListDaoImpl.getDao();
	}
	
	//자신의 객체 생성 리턴
	private static IOverdueListService service;
	public static IOverdueListService getService() {
		if(service == null) service = new OverdueListServiceImpl();
		
		return service;
	}
	@Override
	public List<Map<String, Object>> selectBanUserMap() {
		// TODO Auto-generated method stub
		return dao.selectBanUserMap();
	}
	@Override
	public boolean sendWarning(int loanNo) {
		
		
		// 1) db에서 연락처, 이름, dueDate 등 조회
		  Map<String,Object> info = dao.selectLoanContact(loanNo);
		  System.out.println("DEBUG selectLoanContact → " + info);
		  String email    = (String)info.get("email");
		  if(email == null || email.isBlank()) {
			    throw new IllegalStateException("연체자 이메일 주소가 없습니다: loanNo=" + loanNo);
			}
			
		  String userName = (String)info.get("name");
		  Date   dueDate  = (Date)  info.get("dueDate");
		  BigDecimal userNoDec = (BigDecimal) info.get("userNo");
		  int userNo = userNoDec.intValue();
		  
		  long   daysLate = ChronoUnit.DAYS.between(
		                       dueDate.toInstant()
		                              .atZone(ZoneId.systemDefault())
		                              .toLocalDate(),
		                       LocalDate.now());

		  // 2) 메시지 구성
		  String subject = "[도서관] 연체 알림";
		  String body    = String.format(
		    "%s님, 반납일이 %d일 지났습니다. 반납 부탁드립니다.",
		    userName, daysLate);

		  // 3) 외부 API 호출 (임시로 true 리턴해도 OK)
		  boolean emailOk = EmailSender.getInstance().send(email, subject, body);

		  // 4) 이력 남기기
		  Noti noti = new Noti();
		  noti.setUserNo(userNo);
		  noti.setMessage(body);
		  int inserted = dao.insertWarningHistory(noti);

		  return inserted>0;
	}

}
