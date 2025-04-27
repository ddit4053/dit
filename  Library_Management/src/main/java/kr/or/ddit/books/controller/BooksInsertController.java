package kr.or.ddit.books.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.books.service.BooksServiceImp;
import kr.or.ddit.books.service.IBooksService;
import kr.or.ddit.vo.BooksVo;

@WebServlet("/books/bookInsert.do")
public class BooksInsertController extends HttpServlet {

	IBooksService booksService = BooksServiceImp.getInsatance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int start = 1;
        int max =	10;
        int end = 100; // 50페이지까지 불러오고 싶다면 1페이지 부터 10권씩 50페이지까지
        
        Gson gson = new Gson();
        JsonArray allItems = new JsonArray();
        String ttbKey = "ttbypung01732012001";
        StringBuilder allResults = new StringBuilder();

        do {
            String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=" + ttbKey +
                            "&QueryType=Bestseller&MaxResults=" + max +
                            "&start=" + start +
                            "&Cover=Mini&SearchTarget=Book&output=JS&Version=20131101";
            //카테고리 아이디 1230으로 일단 가져오기
            

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
				// while로 반복하기 때문에 item도 반복되서 들어감 그래서 item값만 가져와 저장
			}

            in.close();

            allResults.append(result); // 결과 누적
            start += 1;
            
        } while (start < end); // 종료 조건 수정

        JsonObject responseJson = new JsonObject();
		responseJson.add("item", allItems);
		
		JsonArray items = responseJson.getAsJsonArray("item");
		
		Type listType = new TypeToken<List<BooksVo>>(){}.getType();
		List<BooksVo> bookList = gson.fromJson(items, listType);
		
		for (BooksVo booksVo : bookList) {

			booksService.insertBooks(booksVo);

		}
		//System.out.println(bookList);
		
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(gson.toJson(responseJson));
        
    }
}
