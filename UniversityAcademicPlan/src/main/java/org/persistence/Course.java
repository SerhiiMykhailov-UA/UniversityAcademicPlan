package org.persistence;

import java.io.Serializable;
import java.util.List;

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
	private String name;
	@ManyToMany(mappedBy = "course")
	private List<Student> student;
	@ManyToMany
	private List<Teacher> teacher;
	@OneToMany
	private List<Lecture> lecture;
	@ManyToOne
	private Location location;
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

	public List<Lecture> getLecture() {
	    return lecture;
	}

	public void setLecture(List<Lecture> param) {
	    this.lecture = param;
	}

	public Location getLocation() {
	    return location;
	}

	public void setLocation(Location param) {
	    this.location = param;
	}

}