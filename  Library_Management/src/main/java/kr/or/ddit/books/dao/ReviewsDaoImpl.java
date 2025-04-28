package kr.or.ddit.books.dao;

import java.util.List;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.ReviewsVo;

public class ReviewsDaoImpl extends MybatisDao implements IReviewsDao {
	private static ReviewsDaoImpl instance;

	private ReviewsDaoImpl() {

	}

	public static ReviewsDaoImpl getInstance() {
		if (instance == null) {
			instance = new ReviewsDaoImpl();
		}
		return instance;
	}

	

	
	@Override
	public List<ReviewsVo> reviewList(int bookNo) {
		// TODO Auto-generated method stub
		return selectList("reviews.reviewList",bookNo);
	}

	@Override
	public void reviewInsert(ReviewsVo reviewsVo) {
		// TODO Auto-generated method stub
		 insert("reviews.reviewInsert", reviewsVo);
	}
}
