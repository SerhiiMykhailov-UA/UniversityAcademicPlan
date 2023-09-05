package ua.foxminded.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.bytebuddy.implementation.bind.annotation.Super;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.MERGE;
import javax.persistence.Column;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
//@Table(name = "student")
public class Student extends Person {

	@Column
	private @NonNull String firstName;
	@Column
	private @NonNull String lastName;
	@EqualsAndHashCode.Exclude
	//@ToString.Exclude
	@ManyToOne(cascade = { PERSIST, MERGE })
	@JoinColumn(name = "groupSt_id", referencedColumnName = "id")
	private GroupSt groupSt;
	@EqualsAndHashCode.Exclude
	//@ToString.Exclude
	@ManyToMany(cascade = {MERGE, PERSIST})
	@JoinTable(
			name = "student_course",
			joinColumns = @JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> course;
	
}