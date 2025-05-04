package kr.or.ddit.books.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.books.service.IReqBookService;
import kr.or.ddit.books.service.ReqBookServiceImpl;
import kr.or.ddit.vo.BooksVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class BookAdminReqInsertController
 */
@WebServlet("/admin/books/approveRequest.do")
public class BookAdminReqInsertController extends HttpServlet {
	IReqBookService reqBookService = ReqBookServiceImpl.getInstance();
	IBooksService booksService = BooksServiceImp.getInsatance();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookAdminReqInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				String reqBookNo = request.getParameter("reqBookNo"); // 신청도서 번호		
				String reqIsbn = request.getParameter("reqIsbn"); // 신청도서 isbn 받아오기
			
				Gson gson = new Gson();
		        JsonArray allItems = new JsonArray();
				
		        String ttbKey = "ttbypung01732012001";
		        String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey="+ttbKey
		        				+ "&Query="+reqIsbn+"&QueryType=ISBN&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101";
	
		        URL url = new URL(apiUrl);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("GET");
	
		        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		        String inputLine;
		        StringBuilder result = new StringBuilder();
	
		        while ((inputLine = in.readLine()) != null) {
		            result.append(inputLine);
		        }
		        
		        JsonParser parser = new JsonParser();
	            JsonObject jsonResponse = parser.parse(result.toString()).getAsJsonObject();
				JsonArray items = jsonResponse.getAsJsonArray("item");
				
				if (items != null) {
					for (int i = 0; i < items.size(); i++) {
						allItems.add(items.get(i));
					}
		        in.close();
		        
		        JsonObject responseJson = new JsonObject();
				responseJson.add("item", allItems);
		        
				
				Type listType = new TypeToken<List<BooksVo>>(){}.getType();
				List<BooksVo> bookList = gson.fromJson(items, listType);
	
				int success =0;
				JsonObject resultJson = new JsonObject();
				
				for (BooksVo booksVo : bookList) {
				        booksService.insertBooksIfNotExist(booksVo);
				        success = reqBookService.updateSuccess(reqBookNo);
			       for(int i=0; i<3; i++) {
			    	   
			    	   int isnertRealBook = booksService.insertRealBook();
			       }
				}
				if(success >0) {
		            resultJson.addProperty("success", true);
		            resultJson.addProperty("message", "신청 도서가 승인되었습니다.");
				}
				
		        response.setContentType("application/json;charset=UTF-8");
		        response.getWriter().write(resultJson.toString());
		}
	
	}
}
