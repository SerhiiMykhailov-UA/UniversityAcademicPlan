package ua.foxminded.entity;

public enum UserType {
	
	STUDENT("student"), TEACHER("teacher");
		
	private String userTipe;
	
	UserType(String userTipe) {
		this.userTipe = userTipe;
	}

	public String getUserTipe() {
		return userTipe;
	}

}
