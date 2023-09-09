package ua.foxminded.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.MERGE;
import javax.persistence.Column;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Student extends Users {

	@Column
	private @NonNull String firstName;
	@Column
	private @NonNull String lastName;
	
	@EqualsAndHashCode.Exclude
	//@ToString.Exclude
	@ManyToOne(cascade = { PERSIST, MERGE })
	@JoinColumn(name = "groups_id", referencedColumnName = "id")
	private Groups groups;
	
	@EqualsAndHashCode.Exclude
//	@ToString.Exclude
	@ManyToMany(cascade = {MERGE, PERSIST})
	@JoinTable(
			name = "student_course",
			joinColumns = @JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> course;
	
}