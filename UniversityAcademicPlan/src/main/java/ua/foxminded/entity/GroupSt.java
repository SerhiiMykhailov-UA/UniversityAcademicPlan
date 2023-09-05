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
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "GroupSt")
public class GroupSt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	@NonNull
	private String name;
	@OneToMany(mappedBy = "groupSt")
	private List<Student> student;
	@ManyToMany
	@JoinTable(name = "groupSt_course", joinColumns = @JoinColumn(name = "groupSt_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> course;

}