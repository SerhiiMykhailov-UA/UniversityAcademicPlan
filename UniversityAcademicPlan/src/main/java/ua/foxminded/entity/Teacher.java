package ua.foxminded.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Teacher extends Users {

	@NonNull
	@Column
	private String firstName;
	@NonNull
	@Column
	private String lastName;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "teacher")
	private List<Course> courses;
	
}