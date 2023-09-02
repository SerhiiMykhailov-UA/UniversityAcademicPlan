package ua.foxminded.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;

	public Course() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String name;
	@ManyToMany(mappedBy = "course")
	private List<Student> student;
	@ManyToMany
	private List<Teacher> teacher;
	@ManyToOne
	private Location location;
	@OneToMany(mappedBy = "course")
	private List<Schedule> schedule;
	@OneToMany(mappedBy = "course")
	private List<Lecture> lecture;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

	public List<Student> getStudent() {
	    return student;
	}

	public void setStudent(List<Student> param) {
	    this.student = param;
	}

	public List<Teacher> getTeacher() {
	    return teacher;
	}

	public void setTeacher(List<Teacher> param) {
	    this.teacher = param;
	}

	public Location getLocation() {
	    return location;
	}

	public void setLocation(Location param) {
	    this.location = param;
	}

	public List<Schedule> getSchedule() {
	    return schedule;
	}

	public void setSchedule(List<Schedule> param) {
	    this.schedule = param;
	}

	public List<Lecture> getLecture() {
	    return lecture;
	}

	public void setLecture(List<Lecture> param) {
	    this.lecture = param;
	}

}