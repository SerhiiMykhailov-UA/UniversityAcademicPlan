package ua.foxminded.entity;

public enum UserType {
	
	ROLE_STUDENT("student"), ROLE_TEACHER("teacher"), ROLE_ADMIN("admin"), ROLE_STUFF("stuff");
		
	private String userTipe;
	
	UserType(String userTipe) {
		this.userTipe = userTipe;
	}

	public String getUserType() {
		return userTipe;
	}

}
