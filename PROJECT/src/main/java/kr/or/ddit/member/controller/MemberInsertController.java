package kr.or.ddit.member.controller;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVo;

/**
 * 이 컨트롤러는 기존의 MemberInsertController를 확장하여,
 * 회원가입 관련 기능을 MemberRegisterController로 이관하고
 * 향후 관리자 전용 회원 등록 기능으로 활용할 수 있도록 준비합니다.
 */
@WebServlet("/admin/member/insert.do")
public class MemberInsertController extends HttpServlet {
    
    private IMemberService memberService = MemberServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 관리자 권한 확인 (세션에서 사용자 정보를 가져와 권한 확인)
        HttpSession session = req.getSession();
        MemberVo loginMember = (MemberVo) session.getAttribute("member");
        
        // 로그인 상태 및 관리자 권한 확인
        if (loginMember == null || !"ADMIN".equals(loginMember.getMemberRole())) {
            resp.sendRedirect(req.getContextPath() + "/?status=unauthorized");
            return;
        }
        
        // 관리자용 회원 등록 페이지로 포워딩
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/admin/memberInsert.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 관리자 권한 확인
        HttpSession session = req.getSession();
        MemberVo loginMember = (MemberVo) session.getAttribute("member");
        
        if (loginMember == null || !"ADMIN".equals(loginMember.getMemberRole())) {
            resp.sendRedirect(req.getContextPath() + "/?status=unauthorized");
            return;
        }
        
        // 폼 데이터 수신 및 처리 로직
        req.setCharacterEncoding("UTF-8");
        
        // TODO: 실제 구현 시 관리자용 회원 등록 로직 작성
        
        // 회원 목록 페이지로 리다이렉트
        resp.sendRedirect(req.getContextPath() + "/admin/member/list.do?status=insert-success");
    }
}