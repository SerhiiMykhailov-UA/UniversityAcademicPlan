package org.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.persistence.Course;
import java.util.List;
import javax.persistence.OneToMany;

@Entity
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	public Location() {
	}

	@Id
	private long id;
	private String name;
	@OneToMany(mappedBy = "location")
	private List<Course> course;
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

	public List<Course> getCourse() {
	    return course;
	}

	public void setCourse(List<Course> param) {
	    this.course = param;
	}

}