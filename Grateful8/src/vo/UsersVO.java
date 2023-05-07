package vo;

public class UsersVO {
	private String userId;
	private String userPw;
	
	public UsersVO(String userId,String userPw) {
		this.userId = userId;
		this.userPw = userPw;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserPw() {
		return userPw;
	}

	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", userPw=" + userPw + "]";
	}
	
}
