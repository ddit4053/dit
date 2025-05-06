package kr.or.ddit.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/uploads/*")
public class ImageProviderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        System.out.println("ImageProviderServlet: 요청 경로 = " + pathInfo);
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // 요청 경로에서 첫 번째 '/'를 제거
        String filePath = pathInfo.substring(1);
        
        // C:/upload/ 경로를 앞에 추가
        File file = new File("C:/upload/" + filePath);
        System.out.println("실제 파일 경로: " + file.getAbsolutePath());
        
        if (!file.exists()) {
            System.out.println("파일이 존재하지 않음: " + file.getAbsolutePath());
            // 파일이 없는 경우 기본 이미지 제공
            file = new File(request.getServletContext().getRealPath("/resource/img/no-image.jpg"));
            
            if (!file.exists()) {
                System.out.println("기본 이미지도 존재하지 않음: " + file.getAbsolutePath());
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
        
        // 파일 확장자에 따른 MIME 타입 설정
        String fileName = file.getName();
        String contentType = getServletContext().getMimeType(fileName);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        
        response.setContentType(contentType);
        response.setContentLength((int) file.length());
        
        // 파일 전송
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}