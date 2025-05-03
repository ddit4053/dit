package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.board.service.FileServiceImpl;
import kr.or.ddit.board.service.IFileService;
import kr.or.ddit.vo.File_StorageVo;
import kr.or.ddit.vo.UsersVo;

/**
 * 파일 관련 요청을 처리하는 서블릿
 * - /file/list: 첨부파일 목록 조회
 * - /file/download: 파일 다운로드
 * - /file/upload: 파일 업로드
 * - /file/delete: 파일 삭제
 * - /file/createGroup: 파일 그룹 생성
 */
@WebServlet("/file/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50)    // 50MB
public class FileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IFileService fileService = FileServiceImpl.getInstance();
    
    // 허용된 파일 확장자 목록
    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(
        Arrays.asList("jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "zip")
    );
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        try {
            // URL에 따라 적절한 메서드 호출
            switch (pathInfo) {
                case "/list":
                    getFileList(req, resp);
                    break;
                case "/download":
                    downloadFile(req, resp);
                    break;
                case "/delete":
                    deleteFile(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (NumberFormatException e) {
            sendErrorResponse(resp, "유효하지 않은 숫자 형식입니다: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            sendErrorResponse(resp, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, "서버 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        try {
            // 사용자 인증 확인
            if (!isAuthenticated(req)) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
                return;
            }
            
            switch (pathInfo) {
                case "/upload":
                    uploadFile(req, resp);
                    break;
                case "/delete":
                    deleteFile(req, resp);
                    break;
                case "/createGroup":
                    createFileGroup(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (NumberFormatException e) {
            sendErrorResponse(resp, "유효하지 않은 숫자 형식입니다: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            sendErrorResponse(resp, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, "서버 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 사용자 인증 상태 확인
     */
    private boolean isAuthenticated(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }
    
    /**
     * 첨부파일 목록 조회
     */
    private void getFileList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        String fileGroupNumStr = req.getParameter("fileGroupNum");
        if (fileGroupNumStr == null || fileGroupNumStr.trim().isEmpty()) {
            throw new IllegalArgumentException("파일 그룹번호가 제공되지 않았습니다.");
        }
        
        int fileGroupNum = Integer.parseInt(fileGroupNumStr);
        List<File_StorageVo> fileList = fileService.getFilesByGroupNum(fileGroupNum);
        
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(fileList);
        
        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse);
        }
    }
    
    /**
     * 파일 다운로드
     */
    private void downloadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileNoStr = req.getParameter("fileNo");
        if (fileNoStr == null || fileNoStr.trim().isEmpty()) {
            throw new IllegalArgumentException("파일 번호가 제공되지 않았습니다.");
        }
        
        int fileNo = Integer.parseInt(fileNoStr);
        File_StorageVo file = fileService.getFileByNo(fileNo);
        
        if (file == null) {
            throw new IllegalArgumentException("요청한 파일을 찾을 수 없습니다.");
        }
        
        // 삭제된 파일인지 확인
        if ("Y".equals(file.getDelYn())) {
            throw new IllegalArgumentException("이미 삭제된 파일입니다.");
        }
        
        // 파일 다운로드 응답 설정
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + 
            new String(file.getOrgName().getBytes("UTF-8"), "ISO-8859-1") + "\"");
        
        // 파일 다운로드 서비스 호출
        boolean success = fileService.downloadFile(file, resp.getOutputStream());
        
        if (!success) {
            throw new IOException("파일 다운로드 중 오류가 발생했습니다.");
        }
    }
    
    /**
     * 파일 업로드
     */
    private void uploadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        // 요청 파라미터 검증
        String referenceType = req.getParameter("referenceType");
        String referenceIdStr = req.getParameter("referenceId");
        
        if (referenceType == null || referenceType.trim().isEmpty()) {
            throw new IllegalArgumentException("참조 유형이 제공되지 않았습니다.");
        }
        
        if (referenceIdStr == null || referenceIdStr.trim().isEmpty()) {
            throw new IllegalArgumentException("참조 ID가 제공되지 않았습니다.");
        }
        
        int referenceId = Integer.parseInt(referenceIdStr);
        
        // 파일 확장자 검증 로직 추가
        boolean hasInvalidFile = req.getParts().stream()
            .filter(part -> part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty())
            .anyMatch(part -> {
                String fileName = part.getSubmittedFileName();
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                return !ALLOWED_EXTENSIONS.contains(extension);
            });
        
        if (hasInvalidFile) {
            throw new IllegalArgumentException("허용되지 않는 파일 형식이 포함되어 있습니다.");
        }
        
        // 파일 업로드 처리
        List<File_StorageVo> uploadedFiles = fileService.uploadFiles(req, referenceType, referenceId);
        
        // 업로드된 파일 정보 응답
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("files", uploadedFiles);
        response.put("fileGroupNum", uploadedFiles.isEmpty() ? 0 : uploadedFiles.get(0).getFileGroupNum());
        
        // JSON 응답 전송
        Gson gson = new Gson();
        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(response));
        }
    }
    
    /**
     * 파일 삭제
     */
    private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        Map<String, Object> response = new HashMap<>();
        
        // 파일 번호 또는 파일 그룹 번호로 삭제
        String fileNoStr = req.getParameter("fileNo");
        String fileGroupNumStr = req.getParameter("fileGroupNum");
        
        boolean success = false;
        
        if (fileNoStr != null && !fileNoStr.trim().isEmpty()) {
            // 단일 파일 삭제
            int fileNo = Integer.parseInt(fileNoStr);
            
            // 파일 소유자 확인 (필요한 경우)
            File_StorageVo file = fileService.getFileByNo(fileNo);
            if (file != null) {
                checkFileOwnership(req, file);
            }
            
            success = fileService.deleteFile(fileNo);
            response.put("message", success ? "파일이 성공적으로 삭제되었습니다." : "파일 삭제에 실패했습니다.");
        } else if (fileGroupNumStr != null && !fileGroupNumStr.trim().isEmpty()) {
            // 파일 그룹 전체 삭제
            int fileGroupNum = Integer.parseInt(fileGroupNumStr);
            
            // 파일 그룹 소유권 확인 (필요한 경우)
            List<File_StorageVo> files = fileService.getFilesByGroupNum(fileGroupNum);
            if (!files.isEmpty()) {
                checkFileOwnership(req, files.get(0));
            }
            
            success = fileService.deleteFilesByGroupNum(fileGroupNum);
            response.put("message", success ? "파일 그룹이 성공적으로 삭제되었습니다." : "파일 그룹 삭제에 실패했습니다.");
        } else {
            throw new IllegalArgumentException("파일 번호 또는 파일 그룹 번호가 제공되지 않았습니다.");
        }
        
        response.put("success", success);
        
        Gson gson = new Gson();
        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(response));
        }
    }
    
    /**
     * 파일 소유권 확인
     * 관리자는 모든 파일에 접근 가능
     */
    private void checkFileOwnership(HttpServletRequest req, File_StorageVo file) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            UsersVo user = (UsersVo) session.getAttribute("user");
            if (user != null) {
                // 관리자는 모든 파일 삭제 가능 (isAdmin 메서드는 구현 필요)
                if (isAdmin(user)) {
                    return;
                }
                
                // 본인 파일만 삭제 가능
                if (user.getUserNo() != file.getUserNo()) {
                    throw new IllegalArgumentException("이 파일을 삭제할 권한이 없습니다.");
                }
            }
        }
    }
    
    /**
     * 관리자 권한 확인
     */
    private boolean isAdmin(UsersVo user) {
        // 사용자의 역할이나 권한 수준에 따라 관리자 여부 결정
        // 예: return "ADMIN".equals(user.getRole());
        return false; // 실제 구현 필요
    }
    
    /**
     * 새 파일 그룹 생성
     */
    private void createFileGroup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        int fileGroupNum = fileService.createFileGroup();
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("fileGroupNum", fileGroupNum);
        
        Gson gson = new Gson();
        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(response));
        }
    }
    
    /**
     * 에러 응답 전송
     */
    private void sendErrorResponse(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        
        Gson gson = new Gson();
        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(errorResponse));
        }
    }
}