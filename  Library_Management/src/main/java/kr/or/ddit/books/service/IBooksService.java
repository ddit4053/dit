package kr.or.ddit.books.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BooksVo;
import kr.or.ddit.vo.ReviewsVo;

public interface IBooksService {
	public int insertBooks (BooksVo vo);

	public List<BooksVo> listBooks(Map<String, Object> map);

	public List<BooksVo> searchBookList(Map<String, Object> map);

	public BooksVo booksDetail(BooksVo bookvo);

	public List<BooksVo> mainSearchBookList(Map<String, Object> mapMain);

	public int countSearchBook(Map<String, Object> map);

	public int countMainSearchBook(Map<String, Object> map); // 메인 검색 결과 수

	public void insertBooksIfNotExist(BooksVo bookList);


	
}
