package ua.foxminded.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.foxminded.entity.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UsersDto {
	private long id;
	@NonNull
	private String nickName;
	@NonNull
	private String password;
	private UserType userType;
	
}
