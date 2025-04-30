package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.board.service.FileServiceImpl;
import kr.or.ddit.board.service.IFileService;
import kr.or.ddit.vo.FileGroupVo;
import kr.or.ddit.vo.FileVo;

/**
 * 파일 관련 요청을 처리하는 서블릿
 * - /file/list: 첨부파일 목록 조회
 * - /file/download: 파일 다운로드
 */
@WebServlet("/file/*")
public class FileServlet extends HttpServlet {
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
            } else {
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
            List<FileGroupVo> fileList = fileService.getFilesByGroupNum(fileGroupNum);
            
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
            FileVo file = fileService.getFileByNo(fileNo);
            
            if (file == null) {
                throw new IllegalArgumentException("요청한 파일을 찾을 수 없습니다.");
            }
            
            // 파일 다운로드 응답 설정
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + 
                new String(file.getFileName().getBytes("UTF-8"), "ISO-8859-1") + "\"");
            
            // 파일 다운로드 로직 구현
            fileService.downloadFile(file, resp.getOutputStream());
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