package kr.or.ddit.users.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.UsersVo;


public interface IUserService {
	
	public UsersVo login(UsersVo user);
	
	public UsersVo findUserByKakaoId(String kakaoId);
	
	public boolean registerKakaoUser(UsersVo user);
	
	public List<UsersVo> getUserList();
	
	public boolean registerUser(UsersVo user);
	
	public boolean isIdAvailable(String userId);
	
	public String findUserIdByNameAndEmail(String name, String email);
	
	public UsersVo findUserByIdAndEmail(String userId, String email);
	
	public boolean savePasswordResetToken(int userNo, String pwToken);
	
	public boolean resetPasswordWithToken(int userNo, String pwToken, String newPassword);
	
	public boolean checkUserByNameAndEmail(String name, String email);
	
	public boolean saveEmailVerificationCode(String email, String emToken, String purpose);
	
	public boolean verifyEmailCode(String email, String emToken, String purpose);
	
	public String findUserIdByEmail(String userId, String email);
	
	public UsersVo findUserByEmail(String email);
	
	public boolean updateUser(UsersVo user);
	
	public UsersVo getUserByNo(int userNo);
	
	public UsersVo authenticate(UsersVo user);
	
	public boolean verifyPassword(int userNo, String password);
    
	public boolean changePassword(int userNo, String newPassword);
	
	public boolean quitUser(int userNo, String password);
	
	public UsersVo findDeletedUserByKakaoId(String kakaoId);
	
	public boolean reactivateUser(int userNo);
	
	public int getTotalBookResCount(int userNo);
	
	public int getTotalRoomResCount(int userNo);
	
	public int getTotalBookLoansCount(int userNo);
	
	public int getTotalLoansCount(int userNo);
	
	public int getTotalBookFavoritesCount(int userNo);
	
	public int getTotalBookRequestsCount(int userNo);
	
	public int getTotalResCount(int userNo);
	
	public int getTotalEventReqCount(int userNo);
	
	public int getTotalbookReportCount(int userNo);
	
	public List<Map<String, Object>> bookResList(Map<String, Object> pagingParams);
	
	public List<Map<String, Object>> roomResList(Map<String, Object> pagingParams);
	
	public List<Map<String, Object>> bookLoansList(Map<String, Object> pagingParams);
	
	public List<Map<String, Object>> loansList(Map<String, Object> pagingParams);
	
	public List<Map<String, Object>> bookFavoritesList(Map<String, Object> pagingParams);
	
	public List<Map<String, Object>> bookRequestsList(Map<String, Object> pagingParams);
	
	public List<Map<String, Object>> resList(Map<String, Object> pagingParams);
	
	public List<Map<String, Object>> eventReqList(Map<String, Object> pagingParams);
	
	public List<Map<String, Object>> bookReportList(Map<String, Object> pagingParams);
	
	public boolean requestLoanExtension(Map<String, Object> params);
	
	public boolean deleteFavorite(Map<String, Object> params);

}
