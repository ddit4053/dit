package kr.or.ddit.users.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.util.MybatisDao;
import kr.or.ddit.vo.UsersVo;

public class UserDaoImpl extends MybatisDao implements IUserDao{
	
	private static UserDaoImpl instance;

	private UserDaoImpl() {

	}

	public static UserDaoImpl getInstance() {
		if (instance == null) {
			instance = new UserDaoImpl();
		}
		return instance;
	}

	@Override
    public UsersVo login(UsersVo user) {
        return selectOne("user.login", user);
    }
    
    @Override
    public UsersVo findUserByKakaoId(String kakaoId) {
        return selectOne("user.findByKakaoId", kakaoId);
    }
    
    @Override
    public boolean registerKakaoUser(UsersVo user) {
       
        int result = update("user.registerKakaoUser", user);
        return result > 0;
    }

    @Override
    public List<UsersVo> getUserList() {
        return selectList("user.getUserList");
    }
    
    @Override
    public boolean registerUser(UsersVo user) {
        int result = update("user.registerUser", user);
        return result > 0;
    }
    
    @Override
    public boolean isIdAvailable(String userId) {
        Integer count = selectOne("user.countUserById", userId);
        return count != null && count == 0; 
    }
    
    @Override
    public String findUserIdByNameAndEmail(String name, String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        
        return selectOne("user.findUserIdByNameAndEmail", params);
    }
    
    @Override
    public UsersVo findUserByIdAndEmail(String userId, String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("email", email);
        
        return selectOne("user.findUserByIdAndEmail", params);
    }
    
    @Override
    public boolean savePasswordResetToken(int userNo, String pwToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("pwToken", pwToken);
        params.put("expireDate", new java.sql.Timestamp(System.currentTimeMillis() + (24 * 60 * 60 * 1000))); 
        
        int result = update("user.savePasswordResetToken", params);
        return result > 0;
    }
    
    @Override
    public boolean resetPasswordWithToken(int userNo, String pwToken, String newPassword) {
        Map<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("pwToken", pwToken);
        params.put("newPassword", newPassword);
        
        int result = update("user.resetPasswordWithToken", params);
        return result > 0;
    }
    
    @Override
    public boolean checkUserByNameAndEmail(String name, String email) {
    	System.out.println("검색 중: 이름=[" + name + "], 이메일=[" + email + "]");
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        Integer count = selectOne("user.countUserByNameAndEmail", params);
        System.out.println("검색 결과: " + count + "개 찾음");
        return count != null && count > 0;
    }
    
    @Override
    public boolean saveEmailVerificationCode(String email, String emToken, String purpose) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("emToken", emToken);
        params.put("purpose", purpose);
        params.put("emReqDate", new java.sql.Timestamp(System.currentTimeMillis() + (10 * 60 * 1000))); // 10분 유효
        
        int result = update("user.saveEmailVerificationCode", params);
        return result > 0;
    }

    @Override
    public boolean verifyEmailCode(String email, String emToken, String purpose) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("emToken", emToken);
        params.put("purpose", purpose);
        
        Integer count = selectOne("user.verifyEmailCode", params);
        
        if (count != null && count > 0) {
            
            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("email", email);
            updateParams.put("emToken", emToken);
            updateParams.put("purpose", purpose);
            update("user.updateEmailVerificationStatus", updateParams);
            return true;
        }
        return false;
    }

    @Override
    public String findUserIdByEmail(String userId, String email) {
    	
    	Map<String, Object> param = new HashMap<>();
    	param.put("userId", userId);
    	param.put("email", email);
        return selectOne("user.findUserIdByEmail", param);
    }
    
    @Override
    public UsersVo findUserByEmail(String email) {
        return selectOne("user.findUserByEmail", email);
    }
    
    @Override
    public boolean updateUser(UsersVo user) {
        int result = update("user.updateUser", user);
        return result > 0;
    }
    @Override
    public UsersVo getUserByNo(int userNo) {
        return selectOne("user.getUserByNo", userNo);
    }

	@Override
    public boolean changePassword(int userNo, String newPassword) {
        Map<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("newPassword", newPassword);
        
        int result = update("user.changePassword", params);
        return result > 0;
    }

	@Override
	public boolean quitUser(int userNo, String password) {
	
		Map<String, Object> params = new HashMap<>();
		params.put("userNo", userNo);
        params.put("password", password);
        
        int result = update("user.quitUser", params);
		return result > 0;
	}
	
	@Override
	public UsersVo findDeletedUserByKakaoId(String kakaoId) {
	    return selectOne("user.findDeletedUserByKakaoId", kakaoId);
	}
	
	@Override
	public boolean reactivateUser(int userNo) {
	    int result = update("user.reactivateUser", userNo);
	    return result > 0;
	}

	@Override
	public int getTotalBookResCount(int userNo) {
		
		return selectOne("user.getTotalBookResCount",userNo);
		
	}
	
	@Override
	public int getTotalRoomResCount(int userNo) {
		
		return selectOne("user.getTotalRoomResCount",userNo);
	}
	
	@Override
	public int getTotalBookLoansCount(int userNo) {
	
		return selectOne("user.getTotalBookLoansCount",userNo);
	}
	
	@Override
	public int getTotalLoansCount(int userNo) {
		
		return selectOne("user.getTotalLoansCount",userNo);
	}
	
	@Override
	public int getTotalBookFavoritesCount(int userNo) {
		
		return selectOne("user.getTotalBookFavoritesCount",userNo);
	}
	
	@Override
	public int getTotalBookRequestsCount(int userNo) {
		
		return selectOne("user.getTotalBookRequestsCount",userNo);
	}
	
	@Override
	public int getTotalResCount(int userNo) {
		
		return selectOne("user.getTotalResCount",userNo);
	}
	
	@Override
	public int getTotalEventReqCount(int userNo) {
		
		return selectOne("user.getTotalEventReqCount",userNo);
	}
	
	@Override
	public int getTotalbookReportCount(int userNo) {
		
		return selectOne("user.getTotalbookReportCount",userNo);
	}

	@Override
	public List<Map<String, Object>> bookResList(Map<String, Object> pagingParams) {
		
		return selectList("user.bookResList",pagingParams);
	}
	
	@Override
	public List<Map<String, Object>> roomResList(Map<String, Object> pagingParams) {
		
		return selectList("user.roomResList",pagingParams);
	}

	@Override
	public List<Map<String, Object>> bookLoansList(Map<String, Object> pagingParams) {
		
		return selectList("user.bookLoansList",pagingParams);
	}

	@Override
	public List<Map<String, Object>> loansList(Map<String, Object> pagingParams) {
		
		return selectList("user.loansList",pagingParams);
	}

	@Override
	public List<Map<String, Object>> bookFavoritesList(Map<String, Object> pagingParams) {
		
		return selectList("user.bookFavoritesList",pagingParams);
	}

	@Override
	public List<Map<String, Object>> bookRequestsList(Map<String, Object> pagingParams) {
		
		return selectList("user.bookRequestsList",pagingParams);
	}

	@Override
	public List<Map<String, Object>> resList(Map<String, Object> pagingParams) {
		
		return selectList("user.resList",pagingParams);
	}

	@Override
	public List<Map<String, Object>> eventReqList(Map<String, Object> pagingParams) {
		
		return selectList("user.eventReqList",pagingParams);
	}

	@Override
	public List<Map<String, Object>> bookReportList(Map<String, Object> pagingParams) {
		
		return selectList("user.bookReportList",pagingParams);
	}

}
