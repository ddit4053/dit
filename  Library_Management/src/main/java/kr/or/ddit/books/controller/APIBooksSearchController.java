package kr.or.ddit.books.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/books/APIbookSearch.do")
public class APIBooksSearchController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		        String query = URLEncoder.encode(request.getParameter("query"), "UTF-8");
		        String ttbKey = "ttbypung01732012001";
		        String apiUrl = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=" + ttbKey +
		                        "&Query=" + query + "&QueryType=Title&Cover=Small&SearchTarget=Book&output=JS&Version=20131101";

		        URL url = new URL(apiUrl);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("GET");

		        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		        String inputLine;
		        StringBuilder result = new StringBuilder();

		        while ((inputLine = in.readLine()) != null) {
		            result.append(inputLine);
		        }
		        in.close();

		        response.setContentType("application/json;charset=UTF-8");
		        response.getWriter().write(result.toString());
		        
		    }
		
	
	
}
