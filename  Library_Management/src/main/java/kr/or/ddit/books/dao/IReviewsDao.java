package kr.or.ddit.books.dao;

import java.util.List;

import kr.or.ddit.vo.ReviewsVo;

public interface IReviewsDao {
	public List<ReviewsVo> reviewList(int bookNo);
}
