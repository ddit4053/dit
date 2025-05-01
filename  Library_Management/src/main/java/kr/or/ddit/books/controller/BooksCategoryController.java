package kr.or.ddit.books.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BookCategoriesServiceImpl;
import kr.or.ddit.books.service.IBookCategoriesService;
import kr.or.ddit.vo.BookCategoriesVo;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

/**
 * Servlet implementation class BooksCategoryController
 */
@WebServlet("/books/categoryList")
public class BooksCategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BooksCategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IBookCategoriesService categoriesService = BookCategoriesServiceImpl.getInstance();
		
        String parentIdStr = request.getParameter("parentId"); // 부모 ID
        System.out.println(parentIdStr);
        Integer parentId = null;
        
        if (parentIdStr != null && !parentIdStr.trim().isEmpty()) {
            parentId = Integer.parseInt(parentIdStr);
            
        }
        System.out.println("부모아이디"+parentId);
        
        List<BookCategoriesVo> categoryList = categoriesService.getCategoryListByParent(parentId);

        System.out.println("카테고리 리스트 :"+categoryList);
        response.setContentType("application/json; charset=UTF-8");
        new Gson().toJson(categoryList, response.getWriter());
	}



}
