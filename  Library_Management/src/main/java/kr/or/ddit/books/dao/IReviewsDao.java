package kr.or.ddit.books.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.ReviewsVo;

public interface IReviewsDao {
	public List<Map<String, Object>> reviewList(int bookNo);

	public void reviewInsert(ReviewsVo reviewsVo);

	public void reviewUpdate(ReviewsVo reviewsVo);
}
