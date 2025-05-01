<<<<<<<< HEAD: Library_Management/src/main/java/kr/or/ddit/users/controller/mypage/MypageController.java
package kr.or.ddit.users.controller.mypage;
========
package kr.or.ddit.books.admin;
>>>>>>>> cfc2cb8696889568c1b7500bc6aaece3fc4a7bfe: Library_Management/src/main/java/kr/or/ddit/books/admin/BookAdminController.java

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<<< HEAD: Library_Management/src/main/java/kr/or/ddit/users/controller/mypage/MypageController.java
@WebServlet("/user/mypage.do")
public class MypageController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setAttribute("contentPage", "/WEB-INF/view/user/mypage/updateInfo.jsp");
========

@WebServlet("/admin/books/list")
public class BookAdminController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		req.setAttribute("contentPage", "bookList.jsp");
>>>>>>>> cfc2cb8696889568c1b7500bc6aaece3fc4a7bfe: Library_Management/src/main/java/kr/or/ddit/books/admin/BookAdminController.java
		

		ServletContext ctx = req.getServletContext();
<<<<<<<< HEAD: Library_Management/src/main/java/kr/or/ddit/users/controller/mypage/MypageController.java
		ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
========
		ctx.getRequestDispatcher("/WEB-INF/view/admin/book/book.jsp").forward(req, resp);
>>>>>>>> cfc2cb8696889568c1b7500bc6aaece3fc4a7bfe: Library_Management/src/main/java/kr/or/ddit/books/admin/BookAdminController.java
	}
}
