package kr.or.ddit.books.favorite;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.FavoriteServiceImpl;
import kr.or.ddit.books.service.IFavoriteService;
import kr.or.ddit.vo.BookFavoritesVo;

@WebServlet("/books/detail/favoriteInsert")
public class FavoriteInsertController extends HttpServlet{
	IFavoriteService favoriteService = FavoriteServiceImpl.getInstance();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userNoStr = req.getParameter("userNo");
		int userNo = Integer.parseInt(userNoStr);
		
		String bookNoStr = req.getParameter("bookNo");
		int bookNo = Integer.parseInt(bookNoStr);
		
		BookFavoritesVo favorVo = new BookFavoritesVo();
		
		favorVo.setBookNo(bookNo);
		favorVo.setUserNo(userNo);
		
		String result="";
		int count = favoriteService.favoriteCheck(favorVo); // 이미 등록된게 있는지 확인

	    if (count == 0) {
	        favoriteService.favoriteInsert(favorVo); // 관심 등록
	        result = "insert"; // 등록 완료
	    } else {
	        favoriteService.favoriteDelete(favorVo); // 관심 삭제
	        result = "delete"; // 삭제 완료
	    }
		
	    resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().write(result);
		
	}
}
