package kr.or.ddit.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVo;

@WebServlet(urlPatterns = {"/member/register.do", "/member/checkId.do"})
public class MemberRegisterController extends HttpServlet {
    
    private IMemberService memberService = MemberServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        
        if (requestURI.contains("/member/checkId.do")) {
            // 아이디 중복 확인 처리
            checkIdDuplicate(req, resp);
        } else {
            // 회원가입 페이지 표시
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // POST 요청 - 회원가입 처리
        req.setCharacterEncoding("UTF-8");
        
        // 폼 데이터 가져오기
        String memberId = req.getParameter("memberId");
        String memberPw = req.getParameter("memberPw");
        String memberName = req.getParameter("memberName");
        String memberEmail = req.getParameter("memberEmail");
        String memberTel = req.getParameter("memberTel");
        String idChecked = req.getParameter("idChecked");
        
        // 서버 측 유효성 검사
        if (memberId == null || memberId.trim().isEmpty() ||
            memberPw == null || memberPw.trim().isEmpty() ||
            memberName == null || memberName.trim().isEmpty() ||
            memberEmail == null || memberEmail.trim().isEmpty() ||
            idChecked == null || !idChecked.equals("true")) {
            
            req.setAttribute("errorMessage", "모든 필수 항목을 입력하고 아이디 중복 확인을 해주세요.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
            return;
        }
        
        // 아이디 형식 검사 (영문+숫자 4~12자)
        if (!memberId.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{4,12}$")) {
            req.setAttribute("errorMessage", "아이디는 영문자와 숫자를 혼합하여 4~12자 이내로 입력해주세요.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
            return;
        }
        
        // 비밀번호 형식 검사 (영문+숫자+특수문자 8~12자)
        if (!memberPw.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$")) {
            req.setAttribute("errorMessage", "비밀번호는 영문자, 숫자, 특수문자를 혼합하여 8~12자 이내로 입력해주세요.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
            return;
        }
        
        // 이메일 형식 검사
        if (!memberEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            req.setAttribute("errorMessage", "유효한 이메일 주소를 입력해주세요.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
            return;
        }
        
        // 전화번호 형식 검사 (입력된 경우)
        if (memberTel != null && !memberTel.trim().isEmpty() && 
            !memberTel.matches("^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$")) {
            req.setAttribute("errorMessage", "유효한 전화번호 형식이 아닙니다 (예: 010-1234-5678).");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
            return;
        }
        
        // 회원 정보 객체 생성
        MemberVo member = new MemberVo();
        member.setMemberId(memberId);
        member.setMemberPw(memberPw);
        member.setMemberName(memberName);
        member.setMemberEmail(memberEmail);
        
        if (memberTel != null && !memberTel.trim().isEmpty()) {
            member.setMemberTel(memberTel);
        }
        
        // 회원 등록 처리
        boolean isRegistered = memberService.registerMember(member);
        
        if (isRegistered) {
            // 회원가입 성공 시 로그인 페이지로 리다이렉트
            resp.sendRedirect(req.getContextPath() + "/member/list.do?status=register-success");
        } else {
            // 회원가입 실패 시 에러 메시지 표시
            req.setAttribute("errorMessage", "회원 등록 중 오류가 발생했습니다. 다시 시도해주세요.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
        }
    }
    
    // 아이디 중복 확인 처리
    private void checkIdDuplicate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String memberId = req.getParameter("memberId");
        boolean isAvailable = false;
        
        if (memberId != null && !memberId.trim().isEmpty()) {
            // DB에서 아이디 중복 확인
            isAvailable = memberService.isIdAvailable(memberId);
        }
        
        // JSON 응답 반환
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"available\": " + isAvailable + "}");
        out.flush();
    }
}