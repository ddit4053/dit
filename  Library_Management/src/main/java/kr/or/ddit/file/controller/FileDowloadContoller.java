package kr.or.ddit.file.controller;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.file.vo.FileVo;

@WebServlet("/board.do")
public class FileDowloadContoller extends HttpServlet {
	
	FileService fileService = FileService.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileNoStr =  req.getParameter("fileNo");
		
		int fileNo = Integer.parseInt(fileNoStr);
		
		FileVo file = new FileVo();
		file.setFileNo(fileNo);
		
		file = fileService.download(file);
		
		req.setAttribute("file", file);
		
		ServletContext ctx = req.getServletContext();
		ctx.getRequestDispatcher("/download.jsp").forward(req, resp);

		
	}
}
