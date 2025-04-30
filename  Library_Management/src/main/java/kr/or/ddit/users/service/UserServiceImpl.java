package kr.or.ddit.users.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.users.dao.IUserDao;
import kr.or.ddit.users.dao.UserDaoImpl;
import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.UsersVo;

public class UserServiceImpl implements IUserService{
	
	private static UserServiceImpl instance;

	private UserServiceImpl() {

	}

	public static UserServiceImpl getInstance() {
		if (instance == null) {
			instance = new UserServiceImpl();
		}
		return instance;
	}
	
	IUserDao userDao = UserDaoImpl.getInstance();
	
	@Override
	public UsersVo login(UsersVo user) {

		return userDao.login(user);
	}

	@Override
	public UsersVo findUserByKakaoId(String kakaoId) {
		
		return userDao.findUserByKakaoId(kakaoId);
	}

	@Override
	public boolean registerKakaoUser(UsersVo user) {
	
		return userDao.registerKakaoUser(user);
	}

	@Override
	public List<UsersVo> getUserList() {
		
		return userDao.getUserList();
	}

	@Override
	public boolean registerUser(UsersVo user) {
		
		return userDao.registerUser(user);
	}

	@Override
	public boolean isIdAvailable(String userId) {
		
		return userDao.isIdAvailable(userId);
	}

	@Override
	public String findUserIdByNameAndEmail(String name, String email) {
	
		return userDao.findUserIdByNameAndEmail(name, email);
	}

	@Override
	public UsersVo findUserByIdAndEmail(String userId, String email) {
		
		return userDao.findUserByIdAndEmail(userId, email);
	}

	@Override
	public boolean savePasswordResetToken(int userNo, String pwToken) {
	
		return userDao.savePasswordResetToken(userNo, pwToken);
	}

	@Override
	public boolean resetPasswordWithToken(int userNo, String pwToken, String newPassword) {
		
		return userDao.resetPasswordWithToken(userNo, pwToken, newPassword);
	}

	@Override
	public boolean checkUserByNameAndEmail(String name, String email) {
		
		return userDao.checkUserByNameAndEmail(name, email);
	}
	
	@Override
	public boolean saveEmailVerificationCode(String email, String code, String purpose) {
		
		return userDao.saveEmailVerificationCode(email, code, purpose);
	}

	@Override
	public boolean verifyEmailCode(String email, String code, String purpose) {
		
		return userDao.verifyEmailCode(email, code, purpose);
	}

	@Override
	public String findUserIdByEmail(String userId, String email) {
		
		return userDao.findUserIdByEmail(userId, email);
	}

	@Override
	public UsersVo findUserByEmail(String email) {
		
		return userDao.findUserByEmail(email);
	}

	@Override
	public boolean updateUser(UsersVo user) {
	
		return userDao.updateUser(user);
	}

	@Override
	public UsersVo getUserByNo(int userNo) {
	    return userDao.getUserByNo(userNo);
	}

	@Override
    public UsersVo authenticate(UsersVo user) {
       
        return userDao.login(user);
    }
	
	@Override
    public boolean verifyPassword(int userNo, String password) {
        UsersVo user = userDao.getUserByNo(userNo);
        
        if (user != null) {
           
            return password.equals(user.getPassword());
        }
        
        return false;
    }
    
    @Override
    public boolean changePassword(int userNo, String newPassword) {
       
    	return userDao.changePassword(userNo, newPassword);
    }

	@Override
	public boolean quitUser(int userNo, String password) {
		
		return userDao.quitUser(userNo, password);
	}

	@Override
	public UsersVo findDeletedUserByKakaoId(String kakaoId) {
		
		return userDao.findDeletedUserByKakaoId(kakaoId);
	}
    
	@Override
	public boolean reactivateUser(int userNo) {
	    return userDao.reactivateUser(userNo);
	}

	@Override
	public int getTotalBookResCount(int userNo) {
		
		return userDao.getTotalBookResCount(userNo);
	}
	
	@Override
	public int getTotalRoomResCount(int userNo) {
		
		return userDao.getTotalRoomResCount(userNo);
	}
	
	@Override
	public int getTotalBookLoansCount(int userNo) {
		
		return userDao.getTotalBookLoansCount(userNo);
	}
	
	@Override
	public int getTotalLoansCount(int userNo) {
		
		return userDao.getTotalLoansCount(userNo);
	}
	
	@Override
	public int getTotalBookFavoritesCount(int userNo) {
	
		return userDao.getTotalBookFavoritesCount(userNo);
	}
	
	@Override
	public int getTotalBookRequestsCount(int userNo) {
		
		return userDao.getTotalBookRequestsCount(userNo);
	}
	
	@Override
	public int getTotalResCount(int userNo) {
		
		return userDao.getTotalResCount(userNo);
	}
	
	@Override
	public int getTotalEventReqCount(int userNo) {
		
		return userDao.getTotalEventReqCount(userNo);
	}
	
	@Override
	public int getTotalbookReportCount(int userNo) {
		
		return userDao.getTotalbookReportCount(userNo);
	}
	
	@Override
	public int getTotalNotiCount(int userNo) {
		
		return userDao.getTotalNotiCount(userNo);
	}

	@Override
	public List<Map<String, Object>> bookResList(Map<String, Object> pagingParams) {
	
		return userDao.bookResList(pagingParams);
	}

	@Override
	public List<Map<String, Object>> roomResList(Map<String, Object> pagingParams) {
	
		return userDao.roomResList(pagingParams);
	}

	@Override
	public List<Map<String, Object>> bookLoansList(Map<String, Object> pagingParams) {
		
		return userDao.bookLoansList(pagingParams);
	}

	@Override
	public List<Map<String, Object>> loansList(Map<String, Object> pagingParams) {
		
		return userDao.loansList(pagingParams);
	}

	@Override
	public List<Map<String, Object>> bookFavoritesList(Map<String, Object> pagingParams) {
		
		return userDao.bookFavoritesList(pagingParams);
	}

	@Override
	public List<Map<String, Object>> bookRequestsList(Map<String, Object> pagingParams) {
	
		return userDao.bookRequestsList(pagingParams);
	}

	@Override
	public List<Map<String, Object>> resList(Map<String, Object> pagingParams) {
	
		return userDao.resList(pagingParams);
	}

	@Override
	public List<Map<String, Object>> eventReqList(Map<String, Object> pagingParams) {
		
		return userDao.eventReqList(pagingParams);
	}

	@Override
	public List<Map<String, Object>> bookReportList(Map<String, Object> pagingParams) {
		
		return userDao.bookReportList(pagingParams);
	}
	
	@Override
	public List<Map<String, Object>> notiList(Map<String, Object> pagingParams) {
		
		return userDao.notiList(pagingParams);
	}
	
	@Override
	public boolean requestLoanExtension(Map<String, Object> params) {
	    try {
	        MybatisDao dao = new MybatisDao(); 
	        int insertCount = dao.insert("user.insertLoanExtension", params);
	        return insertCount > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean deleteFavorite(Map<String, Object> params) {
	    try {
	        MybatisDao dao = new MybatisDao(); 
	        int deleteFavorite = dao.delete("user.deleteFavorite", params);
	        return deleteFavorite > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean notiMessage(Map<String, Object> params) {
		
		return userDao.notiMessage(params);
	}

}
