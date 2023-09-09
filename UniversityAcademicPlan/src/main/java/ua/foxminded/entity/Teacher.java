package ua.foxminded.entity;

import javax.persistence.CascadeType;
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

@Entity
@Data
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
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "teacher", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<Course> course;
	
}