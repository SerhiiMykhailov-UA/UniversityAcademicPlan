package ua.foxminded.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.foxminded.entity.Course;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	@NonNull
	private String name;
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "course")
	private List<Student> student;
	@EqualsAndHashCode.Exclude
	@ManyToMany
	@JoinTable(
			name = "course_teacher",
			joinColumns = @JoinColumn(name = "course_id"),
			inverseJoinColumns = @JoinColumn(name = "teacher_id"))
	private List<Teacher> teacher;
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Location location;
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<Schedule> schedule;
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<Lecture> lecture;
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "course")
	private List<Groups> groups;

}