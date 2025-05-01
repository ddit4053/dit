package kr.or.ddit.admin.service;

import java.util.List;
import java.util.Map;

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

		return up1>0&&up2>0;
	}

	@Override
	public int rejectLoanExtension(BookLoanApprovalVo vo) {
		// TODO Auto-generated method stub
		return dao.rejectLoanExtension(vo);
	}

}
