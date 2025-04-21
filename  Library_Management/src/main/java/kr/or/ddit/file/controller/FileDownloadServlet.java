package kr.or.ddit.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.file.vo.FileVo;


@WebServlet("/download.do")
public class FileDownloadServlet extends HttpServlet {
	
	FileService fileService = FileService.getInstance();
	
	String rootPath = "D:/upload/";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		String fileNoStr =  req.getParameter("fileNo");
		
		int fileNo = Integer.parseInt(fileNoStr);
		
		FileVo fileVo = new FileVo();
		fileVo.setFileNo(fileNo);
		
		fileVo = fileService.download(fileVo);
		
		
		String fileName = fileVo.getSaveName();
		
		File file = new File(rootPath+fileName);
		
		FileInputStream fis = new FileInputStream(rootPath+fileName);
		
		resp.setContentType("application/octet-stream");
		resp.setContentLength((int)fileVo.getFileSize());
		resp.setHeader("Content-Disposition", "attachment; filename=\""+fileVo.getOrgName()+"\"");
		
		OutputStream out =  resp.getOutputStream();
		
		
		int data = 0 ; 
		
		while( (data = fis.read())!=-1) {
			out.write(data);
		}
		
	
	}
	
}
