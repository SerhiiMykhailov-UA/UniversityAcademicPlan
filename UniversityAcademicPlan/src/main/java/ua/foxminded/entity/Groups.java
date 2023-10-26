package ua.foxminded.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
@RequiredArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Groups {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	@NonNull
	private String name;
	
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "groups")
	private List<Student> student;
	
	@EqualsAndHashCode.Exclude
	@ManyToMany
	@JoinTable(name = "groups_course", joinColumns = @JoinColumn(name = "groups_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> course;

}