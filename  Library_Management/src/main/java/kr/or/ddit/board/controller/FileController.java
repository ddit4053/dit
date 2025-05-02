package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.board.service.FileServiceImpl;
import kr.or.ddit.board.service.IFileService;
import kr.or.ddit.vo.File_StorageVo;

/**
 * 파일 관련 요청을 처리하는 서블릿
 * - /file/list: 첨부파일 목록 조회
 * - /file/download: 파일 다운로드
 * - /file/upload: 파일 업로드
 * - /file/delete: 파일 삭제
 */
@WebServlet("/file/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50)    // 50MB
public class FileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IFileService fileService = FileServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        try {
            // URL에 따라 적절한 메서드 호출
            if (pathInfo.equals("/list")) {
                getFileList(req, resp);
            } else if (pathInfo.equals("/download")) {
                downloadFile(req, resp);
            } else if (pathInfo.equals("/delete")) {
                deleteFile(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
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
            if (pathInfo.equals("/upload")) {
                uploadFile(req, resp);
            } else if (pathInfo.equals("/delete")) {
                deleteFile(req, resp);
            } else if (pathInfo.equals("/createGroup")) {
            	createFileGroup(req,resp);            	
        	}else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 첨부파일 목록 조회
     */
    private void getFileList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String fileGroupNumStr = req.getParameter("fileGroupNum");
            if (fileGroupNumStr == null || fileGroupNumStr.trim().isEmpty()) {
                throw new IllegalArgumentException("파일 그룹번호가 제공되지 않았습니다.");
            }
            
            int fileGroupNum = Integer.parseInt(fileGroupNumStr);
            List<File_StorageVo> fileList = fileService.getFilesByGroupNum(fileGroupNum);
            
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(fileList);
            
            PrintWriter out = resp.getWriter();
            out.print(jsonResponse);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 파일 다운로드
     */
    private void downloadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String fileNoStr = req.getParameter("fileNo");
            if (fileNoStr == null || fileNoStr.trim().isEmpty()) {
                throw new IllegalArgumentException("파일 번호가 제공되지 않았습니다.");
            }
            
            int fileNo = Integer.parseInt(fileNoStr);
            File_StorageVo file = fileService.getFileByNo(fileNo);
            
            if (file == null) {
                throw new IllegalArgumentException("요청한 파일을 찾을 수 없습니다.");
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
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 파일 업로드
     */
    private void uploadFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
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
            
            // 파일 업로드 처리
            List<File_StorageVo> uploadedFiles = fileService.uploadFiles(req, referenceType, referenceId);
            
            // 업로드된 파일 정보 응답
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("files", uploadedFiles);
            response.put("fileGroupNum", uploadedFiles.isEmpty() ? 0 : uploadedFiles.get(0).getFileGroupNum());
            
            // JSON 응답 전송
            Gson gson = new Gson();
            PrintWriter out = resp.getWriter();
            out.print(gson.toJson(response));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 파일 삭제
     */
    private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            Map<String, Object> response = new HashMap<>();
            
            // 파일 번호 또는 파일 그룹 번호로 삭제
            String fileNoStr = req.getParameter("fileNo");
            String fileGroupNumStr = req.getParameter("fileGroupNum");
            
            boolean success = false;
            
            if (fileNoStr != null && !fileNoStr.trim().isEmpty()) {
                // 단일 파일 삭제
                int fileNo = Integer.parseInt(fileNoStr);
                success = fileService.deleteFile(fileNo);
                response.put("message", success ? "파일이 성공적으로 삭제되었습니다." : "파일 삭제에 실패했습니다.");
            } else if (fileGroupNumStr != null && !fileGroupNumStr.trim().isEmpty()) {
                // 파일 그룹 전체 삭제
                int fileGroupNum = Integer.parseInt(fileGroupNumStr);
                success = fileService.deleteFilesByGroupNum(fileGroupNum);
                response.put("message", success ? "파일 그룹이 성공적으로 삭제되었습니다." : "파일 그룹 삭제에 실패했습니다.");
            } else {
                throw new IllegalArgumentException("파일 번호 또는 파일 그룹 번호가 제공되지 않았습니다.");
            }
            
            response.put("success", success);
            
            Gson gson = new Gson();
            PrintWriter out = resp.getWriter();
            out.print(gson.toJson(response));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
        }
    }
    
    /**
     * 새 파일 그룹 생성
     */
    private void createFileGroup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            int fileGroupNum = fileService.createFileGroup();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("fileGroupNum", fileGroupNum);
            
            Gson gson = new Gson();
            PrintWriter out = resp.getWriter();
            out.print(gson.toJson(response));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(resp, e.getMessage());
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
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(errorResponse));
        out.flush();
    }
}