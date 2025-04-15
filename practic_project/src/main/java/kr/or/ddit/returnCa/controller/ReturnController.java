package kr.or.ddit.returnCa.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.returnCa.service.IReturnService;
import kr.or.ddit.returnCa.service.ReturnServiceImpl;
import kr.or.ddit.returnCa.vo.ReturnVo;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class ReturnController
 */
@WebServlet("/Return.do")
public class ReturnController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IReturnService service = ReturnServiceImpl.getService();
		
		List<ReturnVo> list = service.returnList();
		
		request.setAttribute("list", list);
		
		request.getRequestDispatcher("/0414_practice_projec/return.jsp").forward(request, response);
	}

}
