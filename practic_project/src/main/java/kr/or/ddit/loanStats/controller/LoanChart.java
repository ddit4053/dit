package kr.or.ddit.loanStats.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.loanStats.service.ILoansService;
import kr.or.ddit.loanStats.service.LoansServiceImpl;
import kr.or.ddit.loanStats.vo.LoansVo;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class LoanChart
 */
@WebServlet("/LoanChart.do")
public class LoanChart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanChart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트 전송데이터 받기
		//service객체 얻기
		ILoansService service = LoansServiceImpl.getService();
		
		List<LoansVo> list = service.loansList();
		
		request.setAttribute("list", list);
		
		request.getRequestDispatcher("/0414_practice_projec/loans.jsp").forward(request, response);
	}

	

}
