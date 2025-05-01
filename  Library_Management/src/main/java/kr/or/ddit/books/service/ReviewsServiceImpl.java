package kr.or.ddit.books.service;

import java.util.List;

import kr.or.ddit.books.dao.IReviewsDao;
import kr.or.ddit.books.dao.ReviewsDaoImpl;
import kr.or.ddit.vo.ReviewsVo;

public class ReviewsServiceImpl implements IReviewsService {
	
	IReviewsDao reviewsDao = ReviewsDaoImpl.getInstance();
	
	private static ReviewsServiceImpl instance;

	private ReviewsServiceImpl() {

	}

	public static ReviewsServiceImpl getInstance() {
		if (instance == null) {
			instance = new ReviewsServiceImpl();
		}
		return instance;
	}

	

	@Override
	public List reviewList(int bookNo) {
		// TODO Auto-generated method stub
		return reviewsDao.reviewList(bookNo);
	}

	@Override
	public void reviewInsert(ReviewsVo reviewsVo) {
		// TODO Auto-generated method stub
		 reviewsDao.reviewInsert(reviewsVo);
	}

	@Override
	public void reviewUpdate(ReviewsVo reviewsVo) {
		// TODO Auto-generated method stub
		reviewsDao.reviewUpdate(reviewsVo);
	}

	@Override
	public void reviewDelete(int revNo) {
		// TODO Auto-generated method stub
		reviewsDao.reviewDelete(revNo);
	}

}
