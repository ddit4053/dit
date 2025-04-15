package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.util.EmailSender;
import kr.or.ddit.member.util.VerificationUtil;
import kr.or.ddit.member.vo.MemberVo;

@WebServlet(urlPatterns = {
    "/member/findId.do", 
    "/member/findPassword.do", 
    "/member/resetPassword.do"
})
public class MemberFindController extends HttpServlet {
    
    private IMemberService memberService = MemberServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        
        if (requestURI.contains("/member/findId.do")) {
            // 아이디 찾기 페이지 표시
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/findId.jsp").forward(req, resp);
        } else if (requestURI.contains("/member/findPassword.do")) {
            // 비밀번호 찾기 페이지 표시
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/findPassword.jsp").forward(req, resp);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        req.setCharacterEncoding("UTF-8");
        
        // action 파라미터로 어떤 작업을 수행할지 결정
        String action = req.getParameter("action");
        
        if (requestURI.contains("/member/findId.do")) {
            if ("sendCode".equals(action)) {
                // 아이디 찾기 - 인증 코드 발송
                sendIdVerificationCode(req, resp);
            } else if ("verify".equals(action)) {
                // 아이디 찾기 - 인증 코드 확인 및 아이디 제공
                verifyIdAndShowResult(req, resp);
            } else {
                // 기본 작업 (이전 코드와 호환성 유지)
                findId(req, resp);
            }
        } else if (requestURI.contains("/member/findPassword.do")) {
            if ("sendCode".equals(action)) {
                // 비밀번호 찾기 - 인증 코드 발송
                sendPasswordVerificationCode(req, resp);
            } else if ("verify".equals(action)) {
                // 비밀번호 찾기 - 인증 코드 확인 및 비밀번호 재설정 폼 제공
                verifyPasswordAndShowResetForm(req, resp);
            } else {
                // 기본 작업 (이전 코드와 호환성 유지)
                findPassword(req, resp);
            }
        } else if (requestURI.contains("/member/resetPassword.do")) {
            // 비밀번호 재설정 처리
            resetPassword(req, resp);
        }
    }
    
    // 아이디 찾기 처리 - 1단계: 이메일 인증 코드 전송
    private void sendIdVerificationCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String memberName = req.getParameter("memberName");
        String memberEmail = req.getParameter("memberEmail");
        
        // 이름과 이메일이 일치하는 회원 존재 여부 확인
        boolean memberExists = memberService.checkMemberByNameAndEmail(memberName, memberEmail);
        
        if (memberExists) {
            // 인증 코드 생성
            String verificationCode = VerificationUtil.generateVerificationCode();
            
            // 인증 코드 저장
            boolean isCodeSaved = memberService.saveEmailVerificationCode(memberEmail, verificationCode, "FIND_ID");
            
            if (isCodeSaved) {
                // 이메일 발송 (개발용: 콘솔에 출력)
            	boolean isEmailSent = EmailSender.sendVerificationCode(memberEmail, verificationCode, "FIND_ID");
                
                if (isEmailSent) {
                    req.setAttribute("emailSent", true);
                    req.setAttribute("memberEmail", memberEmail);
                    req.setAttribute("successMessage", "인증 코드가 이메일로 전송되었습니다. 확인 후 입력해주세요.");
                } else {
                    req.setAttribute("errorMessage", "이메일 발송에 실패했습니다. 다시 시도해주세요.");
                }
            } else {
                req.setAttribute("errorMessage", "인증 코드 생성에 실패했습니다. 다시 시도해주세요.");
            }
        } else {
            req.setAttribute("errorMessage", "일치하는 회원 정보가 없습니다. 이름과 이메일을 확인해주세요.");
        }
        
        // 결과 페이지로 포워딩
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/findId.jsp").forward(req, resp);
    }
    
    // 아이디 찾기 처리 - 2단계: 인증 코드 확인 및 아이디 제공
    private void verifyIdAndShowResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String memberEmail = req.getParameter("memberEmail");
        String verificationCode = req.getParameter("verificationCode");
        
        // 인증 코드 확인
        boolean isCodeValid = memberService.verifyEmailCode(memberEmail, verificationCode, "FIND_ID");
        
        if (isCodeValid) {
            // 이메일로 아이디 찾기
        	
            String foundId = memberService.findMemberIdByEmail(memberEmail);
            
            if (foundId != null && !foundId.isEmpty()) {
                // 아이디 마스킹 처리
                String maskedId = maskId(foundId);
                req.setAttribute("foundId", maskedId);
                req.setAttribute("verified", true);
                req.setAttribute("successMessage", "인증이 완료되었습니다. 아이디를 확인해주세요.");
            } else {
                req.setAttribute("errorMessage", "아이디 조회 중 오류가 발생했습니다.");
            }
        } else {
            req.setAttribute("errorMessage", "인증 코드가 유효하지 않습니다. 다시 확인해주세요.");
            req.setAttribute("emailSent", true);
            req.setAttribute("memberEmail", memberEmail);
        }
        
        // 결과 페이지로 포워딩
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/findId.jsp").forward(req, resp);
    }
    
    // 비밀번호 찾기 처리 - 1단계: 이메일 인증 코드 전송
    private void sendPasswordVerificationCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String memberId = req.getParameter("memberId");
        String memberEmail = req.getParameter("memberEmail");
        
        // 아이디와 이메일이 일치하는 회원 존재 여부 확인
        System.out.println("입력 ID: [" + memberId + "]");
        System.out.println("입력 이메일: [" + memberEmail + "]");
        MemberVo member = memberService.findMemberByIdAndEmail(memberId, memberEmail);
        System.out.println("조회 결과: " + (member != null ? "회원 찾음" : "회원 없음"));
        
        if (member != null) {
            // 인증 코드 생성
            String verificationCode = VerificationUtil.generateVerificationCode();
            
            // 인증 코드 저장
            boolean isCodeSaved = memberService.saveEmailVerificationCode(memberEmail, verificationCode, "RESET_PASSWORD");
            
            if (isCodeSaved) {
                // 이메일 발송 (개발용: 콘솔에 출력)
            	boolean isEmailSent = EmailSender.sendVerificationCode(memberEmail, verificationCode, "RESET_PASSWORD");
                
                if (isEmailSent) {
                    req.setAttribute("emailSent", true);
                    req.setAttribute("memberId", memberId);
                    req.setAttribute("memberEmail", memberEmail);
                    req.setAttribute("successMessage", "인증 코드가 이메일로 전송되었습니다. 확인 후 입력해주세요.");
                } else {
                    req.setAttribute("errorMessage", "이메일 발송에 실패했습니다. 다시 시도해주세요.");
                }
            } else {
                req.setAttribute("errorMessage", "인증 코드 생성에 실패했습니다. 다시 시도해주세요.");
            }
        } else {
            req.setAttribute("errorMessage", "일치하는 회원 정보가 없습니다. 아이디와 이메일을 확인해주세요.");
        }
        
        // 결과 페이지로 포워딩
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/findPassword.jsp").forward(req, resp);
    }
    
    // 비밀번호 찾기 처리 - 2단계: 인증 코드 확인 및 비밀번호 재설정 폼 제공
    private void verifyPasswordAndShowResetForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String memberId = req.getParameter("memberId");
        String memberEmail = req.getParameter("memberEmail");
        String verificationCode = req.getParameter("verificationCode");
        
        // 인증 코드 확인
        boolean isCodeValid = memberService.verifyEmailCode(memberEmail, verificationCode, "RESET_PASSWORD");
        
        if (isCodeValid) {
            // 회원 정보 조회
            MemberVo member = memberService.findMemberByIdAndEmail(memberId, memberEmail);
            
            if (member != null) {
                // 비밀번호 재설정 토큰 생성
                String resetToken = UUID.randomUUID().toString();
                
                // 토큰 저장
                boolean isTokenSaved = memberService.savePasswordResetToken(member.getMemberNo(), resetToken);
                
                if (isTokenSaved) {
                    req.setAttribute("verified", true);
                    req.setAttribute("memberNo", member.getMemberNo());
                    req.setAttribute("verifyToken", resetToken);
                    req.setAttribute("successMessage", "인증이 완료되었습니다. 새 비밀번호를 설정해주세요.");
                } else {
                    req.setAttribute("errorMessage", "비밀번호 재설정 처리 중 오류가 발생했습니다.");
                }
            } else {
                req.setAttribute("errorMessage", "회원 정보 조회 중 오류가 발생했습니다.");
            }
        } else {
            req.setAttribute("errorMessage", "인증 코드가 유효하지 않습니다. 다시 확인해주세요.");
            req.setAttribute("emailSent", true);
            req.setAttribute("memberId", memberId);
            req.setAttribute("memberEmail", memberEmail);
        }
        
        // 결과 페이지로 포워딩
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/findPassword.jsp").forward(req, resp);
    }
    
    // 기존 메소드: 아이디 찾기 처리 (이전 코드와 호환성 유지)
    private void findId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String memberName = req.getParameter("memberName");
        String memberEmail = req.getParameter("memberEmail");
        
        // 이름과 이메일로 회원 아이디 찾기
        String foundId = memberService.findMemberIdByNameAndEmail(memberName, memberEmail);
        
        if (foundId != null && !foundId.isEmpty()) {
            // 아이디를 찾았을 경우 (아이디 일부를 *로 마스킹)
            String maskedId = maskId(foundId);
            req.setAttribute("foundId", maskedId);
        } else {
            // 일치하는 회원 정보가 없는 경우
            req.setAttribute("notFound", "일치하는 회원 정보가 없습니다. 회원가입 후 이용해주세요.");
        }
        
        // 결과 페이지로 포워딩
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/findId.jsp").forward(req, resp);
    }
    
    // 기존 메소드: 비밀번호 찾기 처리 (이전 코드와 호환성 유지)
    private void findPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String memberId = req.getParameter("memberId");
        String memberEmail = req.getParameter("memberEmail");
        
        // 아이디와 이메일로 회원 정보 확인
        MemberVo member = memberService.findMemberByIdAndEmail(memberId, memberEmail);
        
        if (member != null) {
            // 회원 정보가 확인된 경우, 비밀번호 재설정 토큰 생성
            String verifyToken = UUID.randomUUID().toString();
            
            // 토큰 저장
            boolean isTokenSaved = memberService.savePasswordResetToken(member.getMemberNo(), verifyToken);
            
            if (isTokenSaved) {
                // 이메일 발송 기능 구현 필요 (여기서는 생략, 실제로는 이메일 발송 로직 추가)
                // ...
                
                req.setAttribute("found", true);
                req.setAttribute("memberNo", member.getMemberNo());
                req.setAttribute("verifyToken", verifyToken);
            } else {
                req.setAttribute("errorMessage", "비밀번호 찾기 처리 중 오류가 발생했습니다.");
            }
        } else {
            // 일치하는 회원 정보가 없는 경우
            req.setAttribute("notFound", "일치하는 회원 정보가 없습니다. 아이디와 이메일을 확인해주세요.");
        }
        
        // 결과 페이지로 포워딩
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/findPassword.jsp").forward(req, resp);
    }
    
    // 비밀번호 재설정 처리
    private void resetPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("비밀번호 재설정 시작");
            int memberNo = Integer.parseInt(req.getParameter("memberNo"));
            String verifyToken = req.getParameter("verifyToken");
            String newPassword = req.getParameter("newPassword");
            
            System.out.println("회원번호: " + memberNo);
            System.out.println("토큰: " + verifyToken);
            System.out.println("새 비밀번호 길이: " + (newPassword != null ? newPassword.length() : 0));
            
            boolean isPasswordReset = memberService.resetPasswordWithToken(memberNo, verifyToken, newPassword);
            System.out.println("비밀번호 재설정 결과: " + isPasswordReset);
            
            if (isPasswordReset) {
                System.out.println("비밀번호 재설정 성공, 로그인 페이지로 포워딩");
                req.setAttribute("status", "password-reset-success");
                req.setAttribute("message", "비밀번호가 성공적으로 재설정되었습니다. 새 비밀번호로 로그인하세요.");
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/resetSuccess.jsp").forward(req, resp);
            } else {
                System.out.println("비밀번호 재설정 실패, 에러 메시지 표시");
                req.setAttribute("errorMessage", "비밀번호 재설정에 실패했습니다. 다시 시도해주세요.");
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/findPassword.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            System.out.println("비밀번호 재설정 처리 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("errorMessage", "처리 중 오류가 발생했습니다: " + e.getMessage());
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/findPassword.jsp").forward(req, resp);
        }
    }
    
    // 아이디 마스킹 처리 (예: abcdef -> abc***)
    private String maskId(String id) {
        if (id == null || id.length() <= 3) {
            return id;
        }
        
        int visibleLength = id.length() / 2;
        if (visibleLength < 3) {
            visibleLength = 3;
        }
        
        String visiblePart = id.substring(0, visibleLength);
        String maskedPart = "*".repeat(id.length() - visibleLength);
        
        return visiblePart + maskedPart;
    }
}