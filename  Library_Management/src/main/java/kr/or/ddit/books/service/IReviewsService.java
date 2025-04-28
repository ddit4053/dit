package kr.or.ddit.books.service;

import java.util.List;

import kr.or.ddit.vo.ReviewsVo;

public interface IReviewsService {
	public List<ReviewsVo> reviewList(int bookNo);

	public void reviewInsert(ReviewsVo reviewsVo);
}
