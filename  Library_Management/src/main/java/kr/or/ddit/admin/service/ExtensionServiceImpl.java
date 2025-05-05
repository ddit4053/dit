package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.admin.controller.EmailSender;
import kr.or.ddit.admin.dao.ExtensionDaoImpl;
import kr.or.ddit.admin.dao.IExtensionDao;
import kr.or.ddit.vo.BookLoanApprovalVo;

public class ExtensionServiceImpl implements IExtensionService {

	//dao 객체 필요
	private IExtensionDao dao;
	
	private ExtensionServiceImpl() {
		dao = ExtensionDaoImpl.getDao();
	}
	
	//자신의 객체 생성 리턴
	private static IExtensionService service;
	public static IExtensionService getService() {
		if( service == null) service = new ExtensionServiceImpl();
		
		return service;
	}
 	
	@Override
	public List<Map<String, Object>> extensionListMap() {
		// TODO Auto-generated method stub
		return dao.extentionListMap();
	}

	@Override
	public boolean approveExtension(BookLoanApprovalVo vo) {
			
			int up2 = dao.extendDueDate(vo);
			int up1 = dao.approveLoanExtension(vo);
			
			if (up1 > 0 && up2 > 0) {
		        Map<String, Object> contact = dao.selectApprovalContact(vo.getApprovalNo());
		        String email = (String) contact.get("email");
		        String name = (String) contact.get("name");
		        String bookTitle = (String) contact.get("bookTitle");

		        String subject = "[도서관] 대출 연장 승인 안내";
		        String body = String.format("%s님, 신청하신 도서 \"%s\"에 대한 연장이 승인되었습니다.", name, bookTitle);
		        EmailSender.getInstance().send(email, subject, body);
		    }

		return up1>0&&up2>0;
	}

	@Override
	public int rejectLoanExtension(BookLoanApprovalVo vo) {

		int result = dao.rejectLoanExtension(vo);

	    if (result > 0) {
	        Map<String, Object> contact = dao.selectApprovalContact(vo.getApprovalNo());
	        String email = (String) contact.get("email");
	        String name = (String) contact.get("name");
	        String bookTitle = (String) contact.get("bookTitle");

	        String subject = "[도서관] 대출 연장 반려 안내";
	        String body = String.format("%s님, 신청하신 도서 \"%s\"에 대한 연장 요청이 반려되었습니다.", name, bookTitle);
	        EmailSender.getInstance().send(email, subject, body);
	    }
	    
		return dao.rejectLoanExtension(vo);
	}

}
