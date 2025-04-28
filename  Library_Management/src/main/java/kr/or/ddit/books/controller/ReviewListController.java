package kr.or.ddit.books.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/books/detail/reviewList")
public class ReviewListController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookBoStr = req.getParameter("bookNo");
		int bookNo = Integer.parseInt(bookBoStr);
		System.out.println("aaaa"+bookNo);
	}
}
