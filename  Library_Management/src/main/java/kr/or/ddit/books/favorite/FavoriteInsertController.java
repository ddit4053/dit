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
		
		favoriteService.farvoriteInsert(favorVo);
		
	}
}
