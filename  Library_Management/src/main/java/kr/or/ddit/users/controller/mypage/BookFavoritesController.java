package kr.or.ddit.users.controller.mypage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user/mypage/bookFavoritesList.do")
public class BookFavoritesController extends AbstractPagingController {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPaging(req, resp);
    }
    
    @Override
    protected int getTotalCount(int userNo) {
        return userService.getTotalBookFavoritesCount(userNo);
    }

    @Override
    protected List<Map<String, Object>> getDataList(Map<String, Object> pagingParams) {
        return userService.bookFavoritesList(pagingParams);
    }

    @Override
    protected String getContentPage() {
        return "bookFavoritesList.jsp";
    }
    
    @Override
    protected String getResultAttributeName() {
        return "bookFavoritesList";
    }
}