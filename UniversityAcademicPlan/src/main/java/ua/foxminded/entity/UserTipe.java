package ua.foxminded.entity;

public enum UserTipe {
	
	USER("user"), TEACHER("teacher");
		
	private String userTipe;
	
	UserTipe(String userTipe) {
		this.userTipe = userTipe;
	}

	public String getUserTipe() {
		return userTipe;
	}

}
