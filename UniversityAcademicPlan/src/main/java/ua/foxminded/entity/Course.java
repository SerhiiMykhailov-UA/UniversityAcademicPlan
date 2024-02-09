package ua.foxminded.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString
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
	@ManyToMany()
	@JoinTable(
			name = "course_teacher",
			joinColumns = @JoinColumn(name = "course_id"),
			inverseJoinColumns = @JoinColumn(name = "teacher_id"))
	private List<Teacher> teacher;
	
	@EqualsAndHashCode.Exclude
	@ManyToOne ()
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Location location;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "course")
	private List<Schedule> schedule;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "course")
	private List<Lecture> lecture;

	@ManyToMany(mappedBy = "course", fetch = FetchType.EAGER)
	private List<Groups> groups;

}