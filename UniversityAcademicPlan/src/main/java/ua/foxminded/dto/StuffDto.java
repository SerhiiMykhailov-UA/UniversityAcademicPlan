package ua.foxminded.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StuffDto extends UsersDto {

	@NonNull
	private String firstName;
	
	@NonNull
	private String lastName;
}
