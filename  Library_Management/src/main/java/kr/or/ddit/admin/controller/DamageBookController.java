package kr.or.ddit.admin.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.admin.service.DamageBookServiceImpl;
import kr.or.ddit.admin.service.IDamageBookService;
import kr.or.ddit.vo.DamagedLostBookVo;
import kr.or.ddit.vo.UsersVo;

@WebServlet({ "/admin/books/damaged", "/admin/books/damaged/list" })
public class DamageBookController extends HttpServlet {
    private IDamageBookService service = DamageBookServiceImpl.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // 1) 현재 등록된 분실/파손 내역 조회
//        List<Map<String,Object>> list = service.damageBookList();
//        System.out.println("DEBUG → damageBookList size = " + list.size());
//
//        // 2) JSP에 전달
//        req.setAttribute("damageList", list);
    	
    	int page = 1;
        int size = 5; // 기본 페이지 사이즈
        
    	String p = req.getParameter("page");
    	String s = req.getParameter("size");
    	
    	if(p != null) {
    		try {
				page = Integer.parseInt(p);
			} catch (NumberFormatException e) {
				page =1;
			}
    	}
    	if(s != null) {
    		try {
    			size = Integer.parseInt(s);
    		} catch (NumberFormatException e) {
    			size =1;
    		}
    	}
    	
    	Map<String, Object> data = service.getDamageListPaged(page,size);
    	req.setAttribute("damageList", data.get("list"));
    	req.setAttribute("paging", data.get("paging"));
        
        // 2) 레이아웃 속성 설정
        req.setAttribute("pageTitle",       "분실/파손 도서");
        req.setAttribute("breadcrumbTitle", "분실/파손 도서 목록");
        req.setAttribute("contentPage",     "/WEB-INF/view/admin/book/damageBook.jsp");

        // 3) 전체 레이아웃으로 포워드
        req.getRequestDispatcher("/WEB-INF/view/admin/book/book.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// 컨트롤러 doPost 맨 앞에 삽입 (테스트 전용)
    			UsersVo admin = new UsersVo();
    			admin.setUserNo(4);       // DB에 존재하는 관리자 userNo
    			admin.setRole("ADMIN");
    			req.getSession().setAttribute("loginUser", admin);

    	
    	
    	// 세션에서 관리자(로그인유저) 꺼내기
        HttpSession session = req.getSession();
        UsersVo loginUser = (UsersVo) session.getAttribute("loginUser");
        //if(loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
        //   resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        //    return;
       // }

        // 1) 폼 파라미터 수집
      
        String realBookParam = req.getParameter("realBook");       // 실제 책 식별키(넘버)
        String status        = req.getParameter("lostStatus");     // "분실" or "파손"
        String description   = req.getParameter("description");    // 비고
        
        
        System.out.println(">> realBookParam = " + realBookParam);
        System.out.println(">> status        = " + status);
        System.out.println(">> description   = " + description);
        
        // 2) VO 채우기
        DamagedLostBookVo vo = new DamagedLostBookVo();
       
        vo.setRealBook(Integer.parseInt(realBookParam));
        vo.setLostStatus(status);
        vo.setDescription(description);
        vo.setUserNo(loginUser.getUserNo());  // “관리자” userNo

        // 3) DB에 등록
        int inserted = service.damageInsert(vo);
        if(inserted > 0) {
            int updated = service.updateDamage();
            session.setAttribute("msg",
                updated > 0
                ? "분실/파손 도서가 등록되고, 책 상태가 갱신되었습니다."
                : "등록은 되었으나 책 상태 갱신에 실패했습니다."
            );
        } else {
            session.setAttribute("msg", "분실/파손 도서 등록 중 오류가 발생했습니다.");
        }

        // 4) 다시 GET으로 redirect 해서 목록 리프레시
        resp.sendRedirect(req.getContextPath() + "/admin/books/damaged/list");
    }
}
